package kbs.apps.mobiledevelopment.mathsforkids
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class QuizResult(
    var username: String,
    var results: List<String>,
    var points: Int
) : Parcelable {

}