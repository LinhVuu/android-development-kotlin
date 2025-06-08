class Book (private var title: String = "", private var author: String = "",
            private var publicationYear: Int = 0, private var isAvailable: Boolean = false) {

    fun showDetails(){
        println(title)
        println("Author: $author")
        println("Publication Year: $publicationYear")
        println("Is Available: $isAvailable")
    }

}

class Library (private var books: Array<Book>) {

    fun addBook(book: Book){
        books += book
    }

    fun displayLibrary(){
        for (book in books) {
            book.showDetails()
        }
    }

    fun borrowBook(){

    }
}

fun main(){
    val bookOne = Book(title = "And then there are none", author = "Agatha Christie", publicationYear = 1980, isAvailable = true)
//    book.showDetails()

    val books: Array<Book> = emptyArray<Book>()
    val library = Library(books)
    library.addBook(bookOne)
//    library.displayLibrary()

    val bookTwo = Book(title = "ABC", author = "Agatha 1", publicationYear = 1996, isAvailable = true)
    library.addBook(bookTwo)
    library.displayLibrary()

}

main()