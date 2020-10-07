package com.example.view

import com.example.core.CryptanalysisAffineCipher
import com.example.core.DecodeStrings
import javafx.beans.binding.Bindings
import javafx.scene.control.TextField
import tornadofx.*



class MainView : View() {
    override val root = borderpane()
    private lateinit var encodedText: TextField
    private var decodedStrings = mutableListOf<DecodeStrings>().asObservable()
    private var decodeStringBtn = button("Пуск") { action {
        decodedStrings.clear()
        decodedStrings.addAll(CryptanalysisAffineCipher.decode(encodedText.text)) }
    }


    init {
        with(root) {
            title = "Криптоанализ аффинного шифра"
            center = form {
                fieldset {
                    hbox {
                        field("Введите строчку") {
                            hbox {
                                encodedText = textfield(){
                                    minWidth = 300.0
                                }
                                decodeStringBtn.disableProperty().bind(Bindings.isEmpty(encodedText.textProperty()))
                            }
                        }
                    }

                    hbox {
                        this += decodeStringBtn
                    }
                    vbox { minHeight = 20.0 }
                    tableview(decodedStrings) {
                        readonlyColumn("Предположение", DecodeStrings::assumption).minWidth(150)
                        readonlyColumn("Первая строчка", DecodeStrings::firstString).minWidth(250)
                        readonlyColumn("Вторая строчка", DecodeStrings::secondString).minWidth(250)
                    }
                }
            }
        }

    }
}
