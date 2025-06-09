package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import java.time.format.DateTimeFormatter
import android.content.Intent

class ViewEmployeeFragment : Fragment(R.layout.fragment_view_employee) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Link to elements on the UI.
        val firstName = view.findViewById<TextView>(R.id.firstName)
        val lastName = view.findViewById<TextView>(R.id.lastName)
        val address = view.findViewById<TextView>(R.id.address)
        val city = view.findViewById<TextView>(R.id.city)
        val phoneNumber = view.findViewById<TextView>(R.id.phoneNumber)
        val email = view.findViewById<TextView>(R.id.email)
        val position = view.findViewById<TextView>(R.id.position)
        val department = view.findViewById<TextView>(R.id.department)
        val manager = view.findViewById<TextView>(R.id.manager)
        val dateHired = view.findViewById<TextView>(R.id.dateHired)
        val startDate = view.findViewById<TextView>(R.id.startDate)
        val endDate = view.findViewById<TextView>(R.id.endDate)
        val latestSalary = view.findViewById<TextView>(R.id.latestSalary)
        val ongoingEmployee = view.findViewById<CheckBox>(R.id.ongoingEmployee)
        val profileImage = view.findViewById<ImageView>(R.id.profileImage)

        // Pass data to the next page.
        val employee: Employee? = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("employee", Employee::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("employee")
        }

        // Format date.
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        // Load data from the database, and display on the UI.
        employee?.let {
            firstName.text = it.firstName
            lastName.text = it.lastName
            address.text = it.address
            city.text = it.city
            phoneNumber.text = it.phone
            email.text = it.email
            position.text = it.position
            department.text = it.department
            manager.text = it.reportTo
            dateHired.text = it.dateHired.format(formatter)
            startDate.text = it.startDate.format(formatter)
            endDate.text = it.endDate?.format(formatter) ?: "N/A"
            latestSalary.text = getString(R.string.salary_format, it.latestSalary)
            ongoingEmployee.isChecked = !it.hasLeftCompany()

            // Display the image.
            if (it.imageUri != null && it.imageUri != "default_photo") {
                if (it.imageUri.startsWith("content://")) {
                    // Try get the image.
                    try {
                        val uri = Uri.parse(employee.imageUri)
                        requireContext().contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        profileImage.setImageURI(uri)

                    } catch (e: SecurityException) {
                        // If error, log the error and use default photo instead.
                        Log.e("ViewEmployeeFragment", "Access denied to image URI", e)
                        profileImage.setImageResource(R.drawable.default_photo)
                    }
                } else {
                    val resId = resources.getIdentifier(it.imageUri, "drawable", requireContext().packageName)
                    if (resId != 0) {
                        profileImage.setImageResource(resId)
                    } else {
                        profileImage.setImageResource(R.drawable.default_photo)
                    }
                }
            } else {
                profileImage.setImageResource(R.drawable.default_photo)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        // Reset the text on button to Update when back to the ViewEmployeeFragment.
        (activity?.findViewById<Button>(R.id.buttonUpdate))?.text = "Update"
    }
}