package kbs.apps.mobiledevelopment.week7
import android.os.Parcelable
import android.text.Editable
import kotlinx.parcelize.Parcelize

@Parcelize
class Person(var name: String?, var age: Int?): Parcelable {

}