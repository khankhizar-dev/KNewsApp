package com.android.knewsapp.news.data.local

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class NewsMigrationTest {
    private val testDb = "migration-test"
    private val version2 = 2
    private val version3 = 3

    @get:Rule
    val helper: MigrationTestHelper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            NewsDatabase::class.java,
            emptyList(),
            FrameworkSQLiteOpenHelperFactory(),
        )

    @Test
    @Throws(IOException::class)
    fun migrate2To3() {
        helper.createDatabase(testDb, version2).apply {
            execSQL(
                "INSERT INTO articles (url, title, publishedAt, sourceName) " +
                    "VALUES ('url1', 'title1', '2024-01-01', 'Source')",
            )
            close()
        }

        helper.runMigrationsAndValidate(testDb, version3, true)
    }
}
