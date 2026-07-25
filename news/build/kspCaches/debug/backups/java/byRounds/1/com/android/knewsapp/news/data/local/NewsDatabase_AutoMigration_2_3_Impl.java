package com.android.knewsapp.news.data.local;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.lang.Override;
import java.lang.SuppressWarnings;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
final class NewsDatabase_AutoMigration_2_3_Impl extends Migration {
  public NewsDatabase_AutoMigration_2_3_Impl() {
    super(2, 3);
  }

  @Override
  public void migrate(@NonNull final SupportSQLiteDatabase db) {
    db.execSQL("ALTER TABLE `articles` ADD COLUMN `isRead` INTEGER NOT NULL DEFAULT 0");
  }
}
