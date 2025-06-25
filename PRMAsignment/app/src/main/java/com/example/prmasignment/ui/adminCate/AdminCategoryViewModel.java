package com.example.prmasignment.ui.adminCate;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.model.Category;
import com.example.prmasignment.repository.AdminCategoryRepository;

import java.util.List;

public class AdminCategoryViewModel extends ViewModel {

    private final AdminCategoryRepository repository;

    public MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    public MutableLiveData<Category> createCategoryResult = new MutableLiveData<>();
    public MutableLiveData<Category> updateCategoryResult = new MutableLiveData<>();
    public MutableLiveData<Boolean> deleteCategoryResult = new MutableLiveData<>();

    public AdminCategoryViewModel(String token) {
        this.repository = new AdminCategoryRepository(token);
    }

    public void fetchCategories() {
        repository.getCategories(categoriesLiveData);
    }

    public void createCategory(Category category) {
        repository.createCategory(category, createCategoryResult);
    }

    public void updateCategory(Category category) {
        repository.updateCategory(category, updateCategoryResult);
    }

    public void deleteCategory(int id) {
        repository.deleteCategory(id, deleteCategoryResult);
    }
}

