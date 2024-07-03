package com.example.tanmayee.ui.task


import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.appcompat.app.AlertDialog
import androidx.compose.material3.Snackbar
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.R
import com.example.mytodo.databinding.FragmentTaskBinding
import com.example.tanmayee.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make

class TaskFragment : Fragment() {
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTaskBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = TaskAdapter(TaskClickListener { taskEntry ->
            findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToUpdateFragment(taskEntry))
        })

        viewModel.getAllTasks.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        binding.apply {
            binding.recyclerView.adapter = adapter

            floatingActionButton.setOnClickListener{
                findNavController().navigate(R.id.action_taskFragment_to_addFragment)
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskEntry = adapter.currentList[position]
                viewModel.deleteTask(taskEntry)

                Snackbar.make(binding.root, "Deleted!", Snackbar.LENGTH_LONG).apply{
                    setAction("Undo"){
                        viewModel.insertTask(taskEntry)
                    }
                    show()
                }
            }
        }).attachToRecyclerView(binding.recyclerView)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_delete_all -> deleteAllItem()
            R.id.action_priority -> viewModel.getAllPriorityTasks.observe(viewLifecycleOwner, Observer { tasks ->
                adapter.submitList(tasks)
            })
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllItem(){
        AlertDialog.Builder(requireContext())
            .setTitle("Delete All")
            .setMessage("Are you sure?")
            .setPositiveButton( "Yes"){dialog, _ ->
                viewModel.deleteAll()
                dialog.dismiss()
            }.setNegativeButton( "No") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }
}
