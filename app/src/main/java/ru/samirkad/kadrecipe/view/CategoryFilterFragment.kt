package ru.samirkad.kadrecipe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.samirkad.kadrecipe.adapter.showCategories
import ru.samirkad.kadrecipe.databinding.CategoryFiltersBinding
import ru.samirkad.kadrecipe.model.Category
import ru.samirkad.kadrecipe.viewModel.RecipeViewModel

class CategoryFilterFragment : Fragment() {

    private val categoryFilter: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = CategoryFiltersBinding.inflate(layoutInflater, container, false).also { binding ->

        with(binding) {
            checkBoxEuropean.text = checkBoxEuropean.context.showCategories(Category.European)
            checkBoxAsian.text = checkBoxAsian.context.showCategories(Category.Asian)
            checkBoxEastern.text = checkBoxEastern.context.showCategories(Category.Eastern)
            checkBoxRussian.text = checkBoxRussian.context.showCategories(Category.Russian)
            checkBoxAmerican.text = checkBoxAmerican.context.showCategories(Category.American)

            binding.ok.setOnClickListener {
                onOkButtonClicked(binding)
            }
        }
    }.root

    private fun onOkButtonClicked(binding: CategoryFiltersBinding) {

        val categoryList = arrayListOf<Category>()
        var checkedCount = 5
        val nothingIsChecked = 0

        if(binding.checkBoxEuropean.isChecked) {
            categoryList.add(Category.European)
            categoryFilter.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(binding.checkBoxAsian.isChecked) {
            categoryList.add(Category.Asian)
            categoryFilter.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(binding.checkBoxEastern.isChecked) {
            categoryList.add(Category.Eastern)
            categoryFilter.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(binding.checkBoxRussian.isChecked) {
            categoryList.add(Category.Russian)
            categoryFilter.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(binding.checkBoxAmerican.isChecked) {
            categoryList.add(Category.American)
            categoryFilter.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(checkedCount == nothingIsChecked) {
            Toast.makeText(activity, "Нельзя убрать все фильтры", Toast.LENGTH_LONG).show()
        } else {
            categoryFilter.showRecipesCategories(categoryList)
            val resultBundle = Bundle(1)
            resultBundle.putParcelableArrayList(CHECKBOX_KEY, categoryList)
            setFragmentResult(CHECKBOX_KEY, resultBundle)
            findNavController().popBackStack()
        }
    }

    companion object {
        const val CHECKBOX_KEY = "checkBoxContent"
    }
}