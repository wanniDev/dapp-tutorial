package me.dapp.fruitshop.config

import me.dapp.fruitshop.common.TxEnv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

@Configuration
class Web3jConfig {
    @Bean
    fun web3j(txEnv: TxEnv): Web3j {
        return Web3j.build(HttpService(txEnv.ethereumUrl))
    }
}