package com.example.teo

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import java.net.URL
import java.util.*


data class ProjectAnalog(
    var quality: String,
    var valuable : String = "",
    var projectX: String = "",
    var projectXB: String = "",
    var analogX: String = "",
    var analogXB: String = ""
) {
    fun clear() {
        valuable = ""
        projectX = ""
        projectXB = ""
        analogX = ""
        analogXB = ""
    }

    fun changeValue(propertyIndex: Int, value: String) {
        when(propertyIndex) {
            1 -> valuable = value
            2 -> projectX = value
            4 -> analogX = value
            else -> throw IllegalArgumentException()
        }
    }
}

data class WorkPlan(
    var description: String,

)
class HelloController : Initializable {
    @FXML private lateinit var welcomeText: Label

    @FXML private lateinit var table1: TableView<ProjectAnalog>

    @FXML private lateinit var quality: TableColumn<ProjectAnalog, String>

    @FXML private lateinit var valuable: TableColumn<ProjectAnalog, String>

    @FXML private lateinit var projX: TableColumn<ProjectAnalog, String>

    @FXML private lateinit var projBX: TableColumn<ProjectAnalog, String>

    @FXML private lateinit var analogX: TableColumn<ProjectAnalog, String>

    @FXML private lateinit var analogBX: TableColumn<ProjectAnalog, String>

    @FXML private lateinit var j1: TextField

    @FXML private lateinit var j2: TextField

    @FXML private lateinit var result1: TextField

    @FXML private lateinit var calculate1: Button

//    @FXML
//    private lateinit var analogBX: TableColumn<ProjectAnalog, String>

    private val projectAnalogs: ObservableList<ProjectAnalog> = FXCollections.observableArrayList(
        ProjectAnalog("Удобство работы"),
        ProjectAnalog("Новизна"),
        ProjectAnalog("Соответствие профилю деятельности\n" +
                "заказчика"),
        ProjectAnalog("Ресурсная эффективность"),
        ProjectAnalog("Надежность (защита данных)"),
        ProjectAnalog("Скорость доступа к данным"),
        ProjectAnalog("Гибкость настройки"),
        ProjectAnalog("Обучаемость персонала"),
        ProjectAnalog("Соотношение стоимость/возможности")
    )

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
    }

    private fun onEditCommit(it: TableColumn.CellEditEvent<ProjectAnalog, String>) {
        val pos = it.tablePosition

        if (it.newValue.toDoubleOrNull() == null) {
            Alert(Alert.AlertType.ERROR, "Input data is not correct").show()
            calculate1.isDisable = true
            projectAnalogs[pos.row].changeValue(pos.column, "")
            table1.refresh()

            return
        }

        calculate1.isDisable = false
        projectAnalogs[pos.row].changeValue(pos.column, it.newValue)
        table1.refresh()
        println("edited row = ${it.rowValue}")
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        quality.cellValueFactory = PropertyValueFactory("quality")

        valuable.cellValueFactory = PropertyValueFactory("valuable")
        valuable.cellFactory = TextFieldTableCell.forTableColumn()
        valuable.setOnEditCommit(this::onEditCommit)

        projX.cellValueFactory = PropertyValueFactory("projectX")
        projX.cellFactory = TextFieldTableCell.forTableColumn()
        projX.setOnEditCommit(this::onEditCommit)

        projBX.cellValueFactory = PropertyValueFactory("projectXB")

        analogX.cellValueFactory = PropertyValueFactory("analogX")
        analogX.cellFactory = TextFieldTableCell.forTableColumn()
        analogX.setOnEditCommit(this::onEditCommit)

        analogBX.cellValueFactory = PropertyValueFactory("analogXB")

        table1.columns.forEach {
            it.style += "-fx-alignment: CENTER;"
        }
        table1.items = projectAnalogs
    }

    fun clear1() {
        projectAnalogs.forEach(ProjectAnalog::clear)

        table1.refresh()
        j1.text = ""
        j2.text = ""
        result1.text = ""
    }

    fun calculate1() {

        projectAnalogs.forEach {
            it.analogXB = (it.analogX.toInt() * it.valuable.toDouble()).toString()
            it.projectXB = (it.projectX.toInt() * it.valuable.toDouble()).toString()
            println(it)
        }

        table1.refresh()

        j1.text = projectAnalogs.fold(0.0) { acc, projectAnalog ->
            acc + projectAnalog.projectXB.toDouble()
        }.toString()

        j2.text = projectAnalogs.fold(0.0) { acc, projectAnalog ->
            acc + projectAnalog.analogXB.toDouble()
        }.toString()

        result1.text = (j1.text.toDouble() / j2.text.toDouble()).toString()
    }
}