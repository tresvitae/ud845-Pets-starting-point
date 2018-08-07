package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;

public class PetCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link PetCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView nameBody = (TextView) view.findViewById(R.id.name);
        TextView summaryPriority = (TextView) view.findViewById(R.id.summary);

        // Extract properties from cursor
        int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_BREED);

        // Read the pet attributes from the Cursor for the current pet
        String name = cursor.getString(nameColumnIndex);
        String breed = cursor.getString(breedColumnIndex);

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(breed)) {
            breed = context.getString(R.string.unknown_breed);
        }

        // Populate fields with extracted properties
        nameBody.setText(name);
        summaryPriority.setText(String.valueOf(breed));
    }
}
