package com.udacity.baking.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.udacity.baking.database.dao.RecipeDao;
import com.udacity.baking.database.entities.IngredientEntity;
import com.udacity.baking.database.entities.RecipeBaseEntity;
import com.udacity.baking.database.entities.StepEntity;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
@Database(entities = { IngredientEntity.class, StepEntity.class, RecipeBaseEntity.class }, version = 1, exportSchema = false)
public abstract class BakingDatabase extends RoomDatabase {

    public static final String NAME_BAKING_DATABASE = "BakingDatabase";

    private static BakingDatabase database;

    public abstract RecipeDao recipeDao();
    /**
     * Build a <c>BakingDatabase</c> on a Singleton Instance.
     * If datafile does not exist, will build it.
     * @param context Context of Service or Activity.
     * @return <c>BakingDatabase</c>
     */
    public static BakingDatabase getDatabase(final Context context) {

        if (database == null) {
            if (database == null) {
                database = Room
                        .databaseBuilder(context
                                , BakingDatabase.class
                                , NAME_BAKING_DATABASE)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }

        return database;
    }

    /**
     * Migrate from:
     * version 1 - using the SQLiteDatabase API
     * to
     * version 2 - using Room
     */
    @VisibleForTesting
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Room uses an own database hash to uniquely identify the database
            // Since version 1 does not use Room, it doesn't have the database hash associated.
            // By implementing a Migration class, we're telling Room that it should use the data
            // from version 1 to version 2.
            // If no migration is provided, then the tables will be dropped and recreated.
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
}
