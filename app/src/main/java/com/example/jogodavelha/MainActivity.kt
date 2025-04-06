package com.example.jogodavelha

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var botoes: Array<Array<Button>>
    private var jogadorAtual = "X"
    private var jogoAtivo = true
    private var tabuleiro = Array(3) { Array(3) { "" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        statusText = findViewById(R.id.textStatus)

        botoes = arrayOf(
            arrayOf(findViewById(R.id.button00), findViewById(R.id.button01), findViewById(R.id.button02)),
            arrayOf(findViewById(R.id.button10), findViewById(R.id.button11), findViewById(R.id.button12)),
            arrayOf(findViewById(R.id.button20), findViewById(R.id.button21), findViewById(R.id.button22))
        )

        for (i in 0..2) {
            for (j in 0..2) {
                botoes[i][j].setOnClickListener {
                    if (jogoAtivo && botoes[i][j].text == "") {
                        botoes[i][j].text = jogadorAtual
                        tabuleiro[i][j] = jogadorAtual

                        if (verificarVitoria()) {
                            statusText.text = "Jogador $jogadorAtual venceu!"
                            jogoAtivo = false
                            desabilitarTabuleiro()
                        } else if (verificarEmpate()) {
                            statusText.text = "Empate!"
                            jogoAtivo = false
                        } else {
                            jogadorAtual = if (jogadorAtual == "X") "O" else "X"
                            statusText.text = "Vez do jogador $jogadorAtual"
                        }
                    }
                }
            }
        }

        val botaoReiniciar = findViewById<Button>(R.id.botaoReiniciar)
        botaoReiniciar.setOnClickListener {
            reiniciarJogo()
        }
    }

    private fun verificarVitoria(): Boolean {
        for (i in 0..2) {
            if (tabuleiro[i][0] == jogadorAtual && tabuleiro[i][1] == jogadorAtual && tabuleiro[i][2] == jogadorAtual) return true
            if (tabuleiro[0][i] == jogadorAtual && tabuleiro[1][i] == jogadorAtual && tabuleiro[2][i] == jogadorAtual) return true
        }
        if (tabuleiro[0][0] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][2] == jogadorAtual) return true
        if (tabuleiro[0][2] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][0] == jogadorAtual) return true
        return false
    }

    private fun verificarEmpate(): Boolean {
        for (linha in tabuleiro) {
            for (celula in linha) {
                if (celula == "") return false
            }
        }
        return true
    }

    private fun desabilitarTabuleiro() {
        for (linha in botoes) {
            for (botao in linha) {
                botao.isEnabled = false
            }
        }
    }

    private fun reiniciarJogo() {
        for (i in 0..2) {
            for (j in 0..2) {
                tabuleiro[i][j] = ""
                botoes[i][j].text = ""
                botoes[i][j].isEnabled = true
            }
        }
        jogadorAtual = "X"
        jogoAtivo = true
        statusText.text = "Vez do jogador X"
    }
}
