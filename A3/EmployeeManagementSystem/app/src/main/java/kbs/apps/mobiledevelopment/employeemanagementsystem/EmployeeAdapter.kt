package kbs.apps.mobiledevelopment.employeemanagementsystem

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class EmployeeAdapter(
    private val onItemClick: (Employee) -> Unit
) : ListAdapter<Employee, EmployeeAdapter.EmployeeViewHolder>(EmployeeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_list_employees, parent, false)
        return EmployeeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }

    class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.email == newItem.email  // or use a unique ID if available
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val initials: TextView = itemView.findViewById(R.id.initials)
        private val fullName: TextView = itemView.findViewById(R.id.fullName)
        private val position: TextView = itemView.findViewById(R.id.position)
        private val photo: ImageView = itemView.findViewById(R.id.imageViewPhoto)

        fun bind(employee: Employee) {
            fullName.text = employee.getFullName()
            initials.text = employee.getInitials()
            position.text = employee.position

            employee.imageUri?.let { imageName ->
                val resId = itemView.context.resources.getIdentifier(imageName, "drawable", itemView.context.packageName)
                if (resId != 0) {
                    photo.setImageResource(resId)
                }
            }

            itemView.setOnClickListener {
                onItemClick(employee)
            }
        }
    }
}