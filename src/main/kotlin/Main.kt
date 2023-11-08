import java.util.Scanner
import kotlin.system.exitProcess

val archivesList = mutableListOf<Archive>()
var currentMenu = Menu.archive
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
    var input = getUserInput(CommandType.command).toInt()
    when (input) {
        0 -> {
            if (currentMenu == Menu.archive)
                createArchive()
            else if (currentMenu == Menu.notesList)
                createNote(currentArchive)
        }
        1 -> {
            exit(currentMenu)
        }
        else -> {
            input = input - 2
            if (currentMenu == Menu.archive) {
                currentMenu = Menu.notesList
                currentArchive = archivesList[input]
            } else if (currentMenu == Menu.notesList){
                currentMenu = Menu.note
                currentNote = currentArchive.notes[input]
            }
        }
    }
}
fun createNote(archive: Archive) {
    var note = ""
    var noteName = ""
    println("Введите название заметки")
    noteName = getUserInput(CommandType.text)
    println("Введите текст заметки")
    note = getUserInput(CommandType.text)
    archive.notes.add(Note(noteName,note))
}
fun exit(curMenu: Menu) {
    when (curMenu) {
        Menu.archive -> exitProcess(-1)
        Menu.notesList -> {
            currentMenu = Menu.archive
            currentArchive = Archive(null, mutableListOf())
            currentNote = Note("", "")
            update()
        }
        Menu.note -> {
            currentMenu = Menu.notesList
            update()
        }
    }
}
fun displayCurrentMenu() {
    when (currentMenu) {
        Menu.archive -> displayArchiveMenu()
        Menu.notesList -> displayNotesListMenu(currentArchive)
        Menu.note -> displayNote()
    }
}
fun displayNote(){
    currentMenu = Menu.note
    println(currentNote.text)
    println("1. Выход")
}
fun displayNotesListMenu(archive: Archive) {
    currentMenu = Menu.notesList
    println("Список заметок:")
    println("0. Создать заметку")
    println("1. Выход")
    archive.notes?.forEachIndexed { ind, i -> println((ind + 2).toString() + ". " + i.name) }
}

fun displayArchiveMenu() {
    currentMenu = Menu.archive
    println("Список архивов:")
    println("0. Создать архив")
    println("1. Выход")
    archivesList?.forEachIndexed { ind, i -> println((ind + 2).toString() + ". " + i.name) }
}

fun createArchive(): Archive {
    println("Введите название архива")
    val res = Archive(getUserInput(CommandType.text), mutableListOf())
    archivesList.add(res)
    currentArchive = res
    return res
}
fun getUserInput(commandType: CommandType): String {
    var input: String = ""
    val archiveCommandCheck = input.isNullOrEmpty() || !input.all { it.isDigit() } || input.toInt() > archivesList.size + 1
    val notesListCommandCheck = input.isNullOrEmpty() || !input.all { it.isDigit() } || input.toInt() > currentArchive.notes.size + 1
    val notesCommmandCheck = input.isNullOrEmpty() || !input.all { it.isDigit() } || input.toInt() != 1
    val wrongInput = "Такого пункта нет. Введите номер пункта: "
    if (CommandType.text == commandType) {
        while (input.isNullOrEmpty()) {
            input = Scanner(System.`in`).nextLine()
            if (input.isNullOrEmpty())
                println("Введите хотя бы один символ:")
        }
    } else if (CommandType.command == commandType) {
        if (currentMenu == Menu.archive) {
            while (archiveCommandCheck) {
                input = Scanner(System.`in`).nextLine()
                if (archiveCommandCheck)
                    println(wrongInput)
            }
        } else if (currentMenu == Menu.notesList){
            while (notesListCommandCheck) {
                input = Scanner(System.`in`).nextLine()
                if (notesListCommandCheck)
                    println(wrongInput)
            }
        } else if (currentMenu == Menu.note){
            while (notesCommmandCheck){
                input = Scanner(System.`in`).nextLine()
                if (notesCommmandCheck)
                    println(wrongInput)
            }
        }
    }
    return input
}
enum class CommandType {
    command,
    text
}
enum class Menu {
    archive,
    notesList,
    note
}