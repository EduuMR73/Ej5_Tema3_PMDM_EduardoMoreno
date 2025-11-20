package com.example.demoarbitrosapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de Hilt para proveer dependencias a nivel de aplicación (Singleton).
 * Aquí le enseñamos a Hilt cómo crear instancias de clases que vienen de
 * librerías externas, como Firebase.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provee una instancia única (Singleton) de FirebaseAuth para toda la aplicación.
     * Hilt usará esta función para inyectar FirebaseAuth donde se necesite (ej. en tus ViewModels).
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    /**
     * Provee una instancia única (Singleton) de FirebaseFirestore para toda la aplicación.
     * Hilt usará esta función para inyectar la base de datos Firestore donde se necesite.
     */
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }
}
