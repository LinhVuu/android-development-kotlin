package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

// Creating a RecyclerView that handles Click Events in Android Studio
// https://www.youtube.com/watch?v=ai9rSGcDhyQ
class EmployeeAdapter(
    private val onItemClick: (Employee) -> Unit
) : ListAdapter<Employee, EmployeeAdapter.EmployeeViewHolder>(EmployeeDiffCallback()) {

    // Called when RecyclerView needs a new ViewHolder to represent an item.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {

        // Initiate the layout for a single employee item view.
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_list_employees, parent, false)

        // Return a new ViewHolder that holds the view.
        return EmployeeViewHolder(itemView)
    }

    // Called by RecyclerView to display data at a specific position.
    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {

        // Get the Employee object at the given position in the list.
        val employee = getItem(position)

        // Bind the employee data to the ViewHolder.
        holder.bind(employee)
    }

    // A DiffUtil callback used to determine changes between old and new lists efficiently.
    class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {

        // Checks if two items represent the same employee (based on unique email).
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.email == newItem.email
        }

        // Checks if the content of two employee objects are the same.
        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Get elements from UI.
        private val initials: TextView = itemView.findViewById(R.id.initials)
        private val fullName: TextView = itemView.findViewById(R.id.fullName)
        private val position: TextView = itemView.findViewById(R.id.position)
        private val photo: ImageView = itemView.findViewById(R.id.imageViewPhoto)


        fun bind(employee: Employee) {

            // Assign the values to the UI.

            // Call functions in Employee Class to get full name and initials.
            fullName.text = employee.getFullName()
            initials.text = employee.getInitials()

            // Get employee's position.
            position.text = employee.position

            // Add image to the employee photo using the image URI.
            // If the photo does not exist, use a default photo.
            if (employee.imageUri != null && employee.imageUri != "default_photo") {
                try {
                    if (employee.imageUri!!.startsWith("content://")) {
                        val uri = Uri.parse(employee.imageUri)
                        itemView.context.contentResolver.openInputStream(uri)?.use {
                            photo.setImageURI(uri)
                        } ?: photo.setImageResource(R.drawable.default_photo)
                    } else {
                        val resId = itemView.context.resources.getIdentifier(employee.imageUri, "drawable", itemView.context.packageName)
                        if (resId != 0) {
                            photo.setImageResource(resId)
                        } else {
                            photo.setImageResource(R.drawable.default_photo)
                        }
                    }
                } catch (e: Exception) {
                    photo.setImageResource(R.drawable.default_photo)
                    e.printStackTrace()
                }
            } else {
                photo.setImageResource(R.drawable.default_photo)
            }

            // When users click on the employee, call the function onItemClick.
            itemView.setOnClickListener {
                onItemClick(employee)
            }
        }
    }
}