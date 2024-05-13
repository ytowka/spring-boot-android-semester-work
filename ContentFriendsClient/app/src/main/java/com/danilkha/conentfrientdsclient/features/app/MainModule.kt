package com.danilkha.conentfrientdsclient.features.app

import com.danilkha.conentfrientdsclient.core.network.NetworkModule
import com.danilkha.conentfrientdsclient.core.storage.StorageModule
import com.danilkha.conentfrientdsclient.features.auth.AuthModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

@ComponentScan("com.danilkha.conentfrientdsclient")
@Module([AuthModule::class, NetworkModule::class, StorageModule::class])
class MainModule