package me.dapp.fruitshop.contract.service

import me.dapp.fruitshop.AbstractTest
import me.dapp.fruitshop.service.AppleTxService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class AppleTxServiceTest: AbstractTest() {
    @Autowired
    private lateinit var appleTxService: AppleTxService

    @BeforeEach
    fun init() {}

    @AfterEach
    fun cleanup() {}

    @Test
    @DisplayName("과일(사과) 구매")
    fun buy() {
        val abiName = "Shop"

        var result = appleTxService.buyApple(abiName)

        assertThat(result).isNotNull()
    }
}