package com.mobile.foodreceipe.singletons

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.mobile.foodreceipe.singletons.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext val context: Context) {

    private object PreferenceKeys{

         val selectedMealType = stringPreferencesKey(Constants.PREFERENCES_MEAL_TYPE)
         val selectedMealTypeId = intPreferencesKey(Constants.PREFERENCES_MEAL_TYPE_ID)
         val selectedDietType = stringPreferencesKey(Constants.PREFERENCES_DIET_TYPE)
         val selectedDietTypeId = intPreferencesKey(Constants.PREFERENCES_DIET_TYPE_ID)
         val backOnline = booleanPreferencesKey(Constants.BACK_ONLINE)

    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    suspend fun saveMealAndDietType(MealType : String,
                                            MealTypeID:Int, DietType:String,
                                            DietTypeID:Int){

        context.dataStore.edit {preferences->
            preferences[PreferenceKeys.selectedMealType]=MealType
            preferences[PreferenceKeys.selectedMealTypeId]=MealTypeID
            preferences[PreferenceKeys.selectedDietType]=DietType
            preferences[PreferenceKeys.selectedDietTypeId]=DietTypeID

        }

    }

    suspend fun saveBackOnline(backOnline:Boolean){
        context.dataStore.edit {preferences->
            preferences[PreferenceKeys.backOnline]=backOnline
        }
    }

    val readBackOnline :Flow<Boolean> = context.dataStore.data
        .catch { exception->
            if(exception is IOException){
                emit(emptyPreferences())
            }
            else
            {
                throw exception
            }
        }
        .map { preference->

            val backOnline=preference[PreferenceKeys.backOnline]?:false
            backOnline
        }


    val readMealAndDietType :Flow<MealAndDietType> = context.dataStore.data
        .catch { exception->
            if(exception is IOException){
                emit(emptyPreferences())
            }
            else
            {
                throw exception
            }
        }
        .map { preference->

            val selectedMealType=preference[PreferenceKeys.selectedMealType]?:Constants.DEFAULT_MEAL_TYPE
            val selectedMealTypeID=preference[PreferenceKeys.selectedMealTypeId]?:0
            val selectedDietType=preference[PreferenceKeys.selectedDietType]?:Constants.DEFAULT_DIET_TYPE
            val selectedDietTypeID=preference[PreferenceKeys.selectedDietTypeId]?:0

            MealAndDietType(selectedMealType,selectedMealTypeID,selectedDietType,selectedDietTypeID)

        }


    data class MealAndDietType(val selectedMealType : String,
    val selectedMealTypeID:Int,
    val selectedDietType:String,
    val selectedDietTypeID:Int)
}