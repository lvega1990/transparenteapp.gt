
package gt.transparente.app.data.provider.transparent.gt.disk.cache;

import android.content.Context;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import gt.transparente.app.data.provider.transparent.gt.disk.cache.serializer.JsonSerializer;
import gt.transparente.app.data.entity.PoliticalPartyEntity;
import gt.transparente.app.data.exception.PoliticalPartyNotFoundException;
import gt.transparente.app.domain.executor.ThreadExecutor;
import rx.Observable;
import rx.Subscriber;

/**
 * {@link PoliticalPartyCache} implementation.
 */
@Singleton
public class PoliticalPartyCacheImpl implements PoliticalPartyCache {

    private static final String SETTINGS_FILE_NAME = "com.fernandocejas.android10.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "political_party_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context mContext;
    private final File mCacheDir;
    private final JsonSerializer mSerializer;
    private final FileManager mFileManager;
    private final ThreadExecutor mThreadExecutor;

    /**
     * Constructor of the class {@link PoliticalPartyCacheImpl}.
     *
     * @param context             A
     * @param cacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager         {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public PoliticalPartyCacheImpl(Context context, JsonSerializer cacheSerializer,
                                   FileManager fileManager, ThreadExecutor executor) {
        if (context == null || cacheSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.mContext = context.getApplicationContext();
        this.mCacheDir = this.mContext.getCacheDir();
        this.mSerializer = cacheSerializer;
        this.mFileManager = fileManager;
        this.mThreadExecutor = executor;
    }

    @Override
    public synchronized Observable<PoliticalPartyEntity> get(final int id) {
        return Observable.create(new Observable.OnSubscribe<PoliticalPartyEntity>() {
            @Override
            public void call(Subscriber<? super PoliticalPartyEntity> subscriber) {
                File entityFile = PoliticalPartyCacheImpl.this.buildFile(id);
                String fileContent = PoliticalPartyCacheImpl.this.mFileManager.readFileContent(entityFile);
                PoliticalPartyEntity entity = PoliticalPartyCacheImpl.this.mSerializer.deserialize(fileContent);

                if (entity != null) {
                    subscriber.onNext(entity);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new PoliticalPartyNotFoundException());
                }
            }
        });
    }

    @Override
    public synchronized void put(PoliticalPartyEntity entity) {
        if (entity != null) {
            File entitiyFile = this.buildFile(entity.getId());
            if (!isCached(entity.getId())) {
                String jsonString = this.mSerializer.serialize(entity);
                this.executeAsynchronously(new CacheWriter(this.mFileManager, entitiyFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int id) {
        File entitiyFile = this.buildFile(id);
        return this.mFileManager.exists(entitiyFile);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public synchronized void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.mFileManager, this.mCacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param id The id to build the file.
     * @return A valid file.
     */
    private File buildFile(int id) {
        return new File(this.mCacheDir.getPath() + File.separator + DEFAULT_FILE_NAME + id);
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.mFileManager.writeToPreferences(this.mContext, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.mFileManager.getFromPreferences(this.mContext, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.mThreadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
