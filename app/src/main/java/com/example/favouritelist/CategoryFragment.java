package com.example.favouritelist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CategoryFragment extends Fragment implements CatogaryRecyclerAdapter.CategoryIsClickedInterface {

    private RecyclerView catogoryRecyclerView;
    private CategoryManager mCategoryManager;

    public CategoryManager getCategoryManager() {
        return mCategoryManager;
    }

    @Override
    public void categoryIsClicked(Category category) {
        listenerObject.categoryIsTapped(category);
    }

    interface OnCategoryInteractionListener{
               void categoryIsTapped(Category category);
          }

          private OnCategoryInteractionListener listenerObject;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof OnCategoryInteractionListener){

            listenerObject=(OnCategoryInteractionListener)context;
            mCategoryManager=new CategoryManager(context);
        }else{
            throw new RuntimeException("The Context or Activity must implement OnCategoryInteractionListener interface");
        }
    }

    public CategoryFragment() {
        // Required empty public constructor
    }


    public static CategoryFragment newInstance() {
         return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Category> categories=mCategoryManager.retrieveCategories();
        if(getView()!=null){
        catogoryRecyclerView=getView().findViewById(R.id.categoryRecyclerView);
        catogoryRecyclerView.setAdapter(new CatogaryRecyclerAdapter(categories, this));
        catogoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listenerObject=null;
    }
    //Helpful method
    public void giveCategoryToManager(Category category){
        mCategoryManager.saveCategory(category);
        CatogaryRecyclerAdapter catogaryRecyclerAdapter=(CatogaryRecyclerAdapter) catogoryRecyclerView.getAdapter();
        catogaryRecyclerAdapter.addCategory(category);
    }

    public void saveCategory(Category category){

        mCategoryManager.saveCategory(category);

        updateRecyclerView();

    }

    private void updateRecyclerView(){
        ArrayList<Category> categories =mCategoryManager.retrieveCategories();
        catogoryRecyclerView.setAdapter(new CatogaryRecyclerAdapter(categories, this));
    }
}