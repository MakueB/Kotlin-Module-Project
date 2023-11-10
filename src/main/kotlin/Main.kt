import java.util.Scanner
import kotlin.system.exitProcess

val archivesList = mutableListOf<Archive>()
var currentMenu = Menu.Archive
var currentArchive = Archive(null, mutableListOf())
var currentNote = Note("", "")
fun main() {
    while (true) {
        update()
    }
}
fun update() {
    displayCurrentMenu()
    println("\nВведите команду")
    var input = getUserInput(CommandType.Command).toInt()
    when (input) {
        0 -> {
            if (currentMenu == Menu.Archive)
                createArchive()
            else if (currentMenu == Menu.NoteList)
                createNote(currentArchive)
        }
        1 -> {
            exit(currentMenu)
        }
        else -> {
            input -= 2
            if (currentMenu == Menu.Archive) {
                currentMenu = Menu.NoteList
                currentArchive = archivesList[input]
            } else if (currentMenu == Menu.NoteList){
                currentMenu = Menu.Note
                currentNote = currentArchive.notes[input]
            }
        }
    }
}
fun createNote(archive: Archive) {
    println("Введите название заметки")
    val noteName: String = getUserInput(CommandType.Text)
    println("Введите текст заметки")
    val note: String = getUserInput(CommandType.Text)
    archive.notes.add(Note(noteName,note))
}
fun exit(curMenu: Menu) {
    when (curMenu) {
        Menu.Archive -> {
            println("До новых встреч!")
            exitProcess(-1)
        }
        Menu.NoteList -> {
            currentMenu = Menu.Archive
            currentArchive = Archive(null, mutableListOf())
            currentNote = Note("", "")
            update()
        }
        Menu.Note -> {
            currentMenu = Menu.NoteList
            update()
        }
    }
}
fun displayCurrentMenu() {
    when (currentMenu) {
        Menu.Archive -> displayArchiveMenu()
        Menu.NoteList -> displayNotesListMenu(currentArchive)
        Menu.Note -> displayNote()
    }
}
fun displayNote(){
    currentMenu = Menu.Note
    println(currentNote.text)
    println("1. Выход")
}
fun displayNotesListMenu(archive: Archive) {
    currentMenu = Menu.NoteList
    println("Список заметок:")
    println("0. Создать заметку")
    println("1. Выход")
    archive.notes.forEachIndexed { ind, i -> println((ind + 2).toString() + ". " + i.name) }
}

fun displayArchiveMenu() {
    currentMenu = Menu.Archive
    println("Список архивов:")
    println("0. Создать архив")
    println("1. Выход")
    archivesList.forEachIndexed { ind, i -> println((ind + 2).toString() + ". " + i.name) }
}

fun createArchive(): Archive {
    println("Введите название архива")
    val res = Archive(getUserInput(CommandType.Text), mutableListOf())
    archivesList.add(res)
    currentArchive = res
    return res
}
fun getUserInput(commandType: CommandType): String {
    var input = ""
    val wrongInput = "Такого пункта нет. Введите номер пункта: "
    if (commandType == CommandType.Text) {
        while (input.isEmpty()) {
            input = Scanner(System.`in`).nextLine()
            if (input.isNullOrEmpty())
                println("Введите хотя бы один символ:")
        }
    } else if (commandType == CommandType.Command) {
        when (currentMenu) {
            Menu.Archive -> {
                while (input.isEmpty() || !input.all { it.isDigit() } || input.toInt() > archivesList.size + 1) {
                    input = Scanner(System.`in`).nextLine()
                    if (input.isEmpty() || !input.all { it.isDigit() } || input.toInt() > archivesList.size + 1)
                        println(wrongInput)
                }
            }
            Menu.NoteList -> {
                while (input.isEmpty() || !input.all { it.isDigit() } || input.toInt() > currentArchive.notes.size + 1) {
                    input = Scanner(System.`in`).nextLine()
                    if (input.isEmpty() || !input.all { it.isDigit() } || input.toInt() > currentArchive.notes.size + 1)
                        println(wrongInput)
                }
            }
            Menu.Note -> {
                while (input.isEmpty() || !input.all { it.isDigit() } || input.toInt() != 1){
                    input = Scanner(System.`in`).nextLine()
                    if (input.isEmpty() || !input.all { it.isDigit() } || input.toInt() != 1)
                        println(wrongInput)
                }
            }
        }
    }
    return input
}
