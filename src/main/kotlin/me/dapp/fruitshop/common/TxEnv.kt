package me.dapp.fruitshop.common

import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class TxEnv(private val env: Environment) {
    private val log = LoggerFactory.getLogger(javaClass)

    val ethereumUrl = "${env.getProperty("ethereum.url")}"
    val adminPrivateKey = "${env.getProperty("ethereum.admin.privateKey")}"

    companion object {
        val GAS_PRICE: BigInteger = BigInteger.valueOf(800000000000L)
        val GAS_LIMIT: BigInteger = BigInteger.valueOf(5000000L)

        const val DEFAULT_GAS_PRICE = 300L
        const val MIN_GAS_FEE = 100_000_000L
        const val TX_END_CHECK_DURATION = 1000L
        const val TX_END_CHECK_RETRY = 10
    }
}