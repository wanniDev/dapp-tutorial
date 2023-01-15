package me.dapp.fruitshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FruitshopApplication

fun main(args: Array<String>) {
    runApplication<FruitshopApplication>(*args)
}
