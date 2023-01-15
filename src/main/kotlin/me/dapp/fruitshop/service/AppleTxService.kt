package me.dapp.fruitshop.service

import com.fasterxml.jackson.databind.ObjectMapper
import me.dapp.fruitshop.common.TxEnv
import me.dapp.fruitshop.common.TxEnv.Companion.GAS_LIMIT
import me.dapp.fruitshop.common.TxEnv.Companion.GAS_PRICE
import org.slf4j.LoggerFactory
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.stereotype.Service
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.generated.Uint16
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger

@Service
class AppleTxService(private val txEnv: TxEnv, private val web3j: Web3j, private val mapper: ObjectMapper) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun buyApple(abiName: String): String {
        val buyer = Credentials.create(txEnv.adminPrivateKey)
        val owner = Credentials.create("0x0bbef4649f40ae7fc7e6deac66cec46cb02b5f6f406c284ff4136417e96b8616")
        val defaultResourceLoader = DefaultResourceLoader()
        val file = defaultResourceLoader.getResource("file:contract/build/contracts/${abiName}.json").file

        val srcValue = mapper.readValue(file, Map::class.java).get("bytecode").toString()
        val ethGetTxCnt = web3j.ethGetTransactionCount(buyer.address, DefaultBlockParameterName.LATEST).send()
        val nonce = ethGetTxCnt.transactionCount
        val gasProvider = StaticGasProvider(GAS_PRICE, GAS_LIMIT)
        val encodeConstructor = FunctionEncoder.encodeConstructor(listOf(Uint16(BigInteger(Integer.valueOf(1).toString()))))
        val rawTransaction = RawTransaction.createTransaction(
            nonce,
            gasProvider.gasPrice,
            gasProvider.gasLimit,
            owner.address,
            Convert.toWei("10", Convert.Unit.ETHER).toBigInteger(),
            srcValue + encodeConstructor
        )
        val manager = RawTransactionManager(web3j, buyer)
        val signAndSend = manager.signAndSend(rawTransaction)
        log.error("signAndSend.error ${if (signAndSend.error != null) signAndSend.error.message else "no error."}")
        val transactionHash = signAndSend.transactionHash
        val receipt = web3j.ethGetTransactionReceipt(transactionHash).send()
        log.error("receipt.error ${if (receipt.error != null) receipt.error.message else "no error."}")
        return if (receipt.result.status == "0x1") "success" else "fail"
    }
}