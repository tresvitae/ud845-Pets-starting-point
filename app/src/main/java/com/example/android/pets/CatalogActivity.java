/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * if doesn't work properly, change imports to this:
 * import android.support.v7.app.LoaderManager;
 * import android.support.v7.content.CursorLoader;
 * import android.support.v7.content.Loader;
 * import android.support.v7.widget.SimpleCursorAdapter;
 */

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PET_LOADER = 0;

    //    private PetDbHelper fieldDbHelper;
    PetCursorAdapter fieldPetCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

//        fieldDbHelper = new PetDbHelper(this);
//        displayDatabaseInfo();


        // Find the ListView which will be populated with the pet data
        ListView petListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        fieldPetCursorAdapter = new PetCursorAdapter(this, null);
        petListView.setAdapter(fieldPetCursorAdapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(PetContract.PetEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        // Kick off the loader:
        getLoaderManager().initLoader(PET_LOADER, null, this);
    }

    // AFTER create Loeaders this method is no longer needed.
//    @Override
//    protected void onStart() {
//        super.onStart();
//        displayDatabaseInfo();
//    }

    private void insertPet() {

        // Deleted:
//        SQLiteDatabase db = fieldDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_NAME, "Toto");
        values.put(PetContract.PetEntry.COLUMN_BREED, "Terrier");
        values.put(PetContract.PetEntry.COLUMN_GENDER, PetContract.PetEntry.TYPE_MALE);
        values.put(PetContract.PetEntry.COLUMN_WEIGHT, 7);

        //Replaced:
//        long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME, null, values);
        Uri insert = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, values);
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(PetContract.PetEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    // AFTER create Loeaders this method is no longer needed.
//    /**
//     * Temporary helper method to display information in the onscreen TextView about the state of
//     * the pets database.
//     */
//    private void displayDatabaseInfo() {
////        /**
////         * Create and/or open a database to read from it
////         *  as same as .open shelter.db in command/terminal.
////         */
////
////        SQLiteDatabase sqLiteDatabase = fieldDbHelper.getReadableDatabase();
//
//        // moved some data to Loader<Cursor> on CreateLoader:
////        String[] projection = { PetContract.PetEntry._ID,
////                PetContract.PetEntry.COLUMN_NAME,
////                PetContract.PetEntry.COLUMN_BREED,
////                PetContract.PetEntry.COLUMN_GENDER,
////                PetContract.PetEntry.COLUMN_WEIGHT };
////        String selection = PetContract.PetEntry.COLUMN_PET_GENDER + “=?”;
////        String[] selectionArgs = new String[] { PetContract.PetEntry.TYPE_FEMALE };
//
//        // Its bad practise.
////        Cursor cursor = sqLiteDatabase.query(PetContract.PetEntry.TABLE_NAME,
////                projection,
////                null, null,
////                null, null, null);
//
//        Cursor cursor = getContentResolver().query(PetContract.PetEntry.CONTENT_URI, projection, null, null, null);
//
//        // Deleted for create CursorAdapter:
////        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
//        // and moved to onCreate method after DELETED this full method:
//        // Find the ListView which will be populated with the pet data
//        ListView petListView = (ListView) findViewById(R.id.list);
//
//
//        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
//        View emptyView = findViewById(R.id.empty_view);
//        petListView.setEmptyView(emptyView);
//
//
//        // Deleted for create CursorAdapter
////        try {
//////            // Display the number of rows in the Cursor (which reflects the number of rows in the
//////            // pets table in the database).
//////            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
//////            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
////
////
////
////                // Create a header in the Text View that looks like this:
////                //
////                // The pets table contains <number of rows in Cursor> pets.
////                // _id - name - breed - gender - weight
////                //
////                // In the while loop below, iterate through the rows of the cursor and display
////                // the information from each column in this order.
////                displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
////                displayView.append(PetContract.PetEntry._ID + " -- " +
////                        PetContract.PetEntry.COLUMN_NAME + " -- " +
////                        PetContract.PetEntry.COLUMN_BREED + " -- " +
////                        PetContract.PetEntry.COLUMN_GENDER + " -- " +
////                        PetContract.PetEntry.COLUMN_WEIGHT + "\n");
////
////                // Figure out the index of each column  | Deleted for create CursorAdapter
//////                int idColumnIndex = cursor.getColumnIndex(PetContract.PetEntry._ID);
//////                int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_NAME);
//////                int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_BREED);
//////                int genderColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_GENDER);
//////                int weightColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_WEIGHT);
////
////                // Iterate through all the returned rows in the cursor
////                while (cursor.moveToNext()) {
////                    // Use that index to extract the String or Int value of the word
////                    // at the current row the cursor is on.
////                    int currentID = cursor.getInt(idColumnIndex);
////                    String currentName = cursor.getString(nameColumnIndex);
////                    String currentBreed = cursor.getString(breedColumnIndex);
////                    int currentGender = cursor.getInt(genderColumnIndex);
////                    int currentWeight = cursor.getInt(weightColumnIndex);
////
////                    // Display the values from each column of the current row in the cursor in the TextView
////                    displayView.append(("\n" + currentID + " -- " +
////                            currentName + " -- " +
////                            currentBreed + " -- " +
////                            currentGender + " -- " +
////                            currentWeight));
////                }
////
////        } finally {
////            // Always close the cursor when you're done reading from it. This releases all its
////            // resources and makes it invalid.
////            cursor.close();
////        }
//        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
//        PetCursorAdapter adapter = new PetCursorAdapter(this, cursor);
//
//        // Attach the adapter to the ListView.
//        petListView.setAdapter(adapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                // AFTER create Loeaders this method is no longer needed to call.
//                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_NAME,
                PetContract.PetEntry.COLUMN_BREED};

        return new CursorLoader(this,
                PetContract.PetEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        fieldPetCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        fieldPetCursorAdapter.swapCursor(null);
    }
}