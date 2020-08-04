package com.example.favouritelist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnCategoryInteractionListener {

    public static final String CATEGORY_OBJECT_KEY="CATEGORY_ITEMS";

    public static final int MAIN_ACTIVITY_REQUEST_CODE=1000;

    private CategoryFragment mCategoryFragment;
    private boolean isTablet=false;
    private CategoryItemsFragment mCategoryItemsFragment;
    FloatingActionButton fab;

    private FrameLayout categoryItemsFragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCategoryFragment=(CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.category_fragment);
        categoryItemsFragmentContainer=findViewById(R.id.category_items_container);

        isTablet=categoryItemsFragmentContainer!=null;

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCreateCategory();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayCreateCategory(){
        AlertDialog.Builder alertbuilder=new AlertDialog.Builder(MainActivity.this);
        final EditText categoryEditText=new EditText(this);
        categoryEditText.setInputType(InputType.TYPE_CLASS_TEXT);

        alertbuilder.setTitle(R.string.alertTitle);
        alertbuilder.setView(categoryEditText);

        alertbuilder.setPositiveButton(R.string.positive_title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Category category=new Category(categoryEditText.getText().toString(),new ArrayList<String>());
               mCategoryFragment.giveCategoryToManager(category);
                dialog.dismiss();
                displayCategoryElements(category);
            }
        });
        alertbuilder.create().show();
    }

    private void displayCategoryElements(Category category){
        if(!isTablet) {
            Intent categoryItemsIntent = new Intent(this, CategoryItemsActivity.class);
            categoryItemsIntent.putExtra(CATEGORY_OBJECT_KEY, category);

            startActivityForResult(categoryItemsIntent, MAIN_ACTIVITY_REQUEST_CODE);
        }else{

                if(mCategoryItemsFragment!=null){
                    getSupportFragmentManager().beginTransaction()
                            .remove(mCategoryItemsFragment).commit();
                    mCategoryItemsFragment=null;
                }

            setTitle(category.getName());
            mCategoryItemsFragment=CategoryItemsFragment.newInstance(category);

            if(mCategoryItemsFragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.category_items_container,mCategoryFragment).addToBackStack(null).commit();

            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayCreateCategoryItemDialog();
                }

                private void displayCreateCategoryItemDialog() {

                    final EditText itemEditText=new EditText(MainActivity.this);
                    itemEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Enter the item name here")
                            .setView(itemEditText)
                            .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String item =itemEditText.getText().toString();
                                    mCategoryItemsFragment.addItemToCategory(item);
                                    dialog.dismiss();
                                }
                            }).create()
                            .show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== MAIN_ACTIVITY_REQUEST_CODE && resultCode== Activity.RESULT_OK){

            if(data!=null){

                mCategoryFragment.saveCategory((Category) data.getSerializableExtra(CATEGORY_OBJECT_KEY));
            }

        }
    }



    @Override
    public void categoryIsTapped(Category category) {
        displayCategoryElements(category);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setTitle(getString(R.string.app_name));
        if(mCategoryItemsFragment.category!=null){

            mCategoryFragment.getCategoryManager().saveCategory(mCategoryItemsFragment.category);
        }

        if(mCategoryItemsFragment!=null){
            getSupportFragmentManager().beginTransaction()
                    .remove(mCategoryItemsFragment).commit();
            mCategoryItemsFragment=null;
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCreateCategory();
            }
        });
    }
}