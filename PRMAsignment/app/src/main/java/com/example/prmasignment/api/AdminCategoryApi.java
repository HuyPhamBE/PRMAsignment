package com.example.prmasignment.api;

import com.example.prmasignment.model.Category;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface AdminCategoryApi {
    String urlApi = "/api/categories";
    @GET(urlApi + "/get_all_categories")
    Call<List<Category>> getCategories();

    @POST(urlApi + "/create_category")
    Call<Category> createCategory(@Body Category category);

    @PUT(urlApi + "/update_category")
    Call<Category> updateCategory(@Body Category category);

    @DELETE(urlApi + "/{id}")
    Call<Void> deleteCategory(@Path("id") int id);
}
