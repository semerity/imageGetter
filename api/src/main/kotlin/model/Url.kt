package image.getter.model

import kotlinx.serialization.Serializable

@Serializable
data class Url(
    val url: String
)

//fun Task.taskAsRow() = """
//    <tr>
//        <td>$name</td><td>$description</td><td>$priority</td>
//    </tr>
//    """.trimIndent()
//
//fun List<Task>.tasksAsTable() = this.joinToString(
//    prefix = "<table rules=\"all\">",
//    postfix = "</table>",
//    separator = "\n",
//    transform = Task::taskAsRow
//)