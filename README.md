# transparenteapp.gt

TransparenteApp utiliza la primera API de datos abiertos de Guatemala, gracias a [Transparente.gt](http://transparente.gt/apigility/documentation/Api-v1)

La Aplicación movil esta desarrollada utilizando la arquitecura limpia propuesta por [Uncle Bob](http://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html)

El proyecto esta divido en 3 capas:
* Capa de Presentacion: Maneja toda las vistas y esta construida con el patron de diseño [MVP](https://github.com/pedrovgs/EffectiveAndroidUI/).
* Capa de dominio: En la capa de dominio se encuentran todas las reglas de negocio construidas con interfaces.
* Capa de datos: Se encarga de recuperar la información que va ser desplegada en la aplicación, se utiliza [repository pattern](http://martinfowler.com/eaaCatalog/repository.html) el cual permite que el origen de la información sea transparente para el usuario.

Librerias utilizadas:
* [Android Design Support Library](http://android-developers.blogspot.com/2015/05/android-design-support-library.html)
* [Dagger 2](http://google.github.io/dagger/)
* [Retrofit](http://square.github.io/retrofit/)
* [OkHttp](square.github.io/okhttp/)
* [ReactiveX](http://reactivex.io/)
* [ButterKnife](http://jakewharton.github.io/butterknife/)
* [gson](https://github.com/google/gson)

Screenshots

![Transparente App](https://raw.githubusercontent.com/punkete1990/transparenteapp.gt/master/images/screen1.png)
![Transparente App2](https://raw.githubusercontent.com/punkete1990/transparenteapp.gt/master/images/screen2.png)


