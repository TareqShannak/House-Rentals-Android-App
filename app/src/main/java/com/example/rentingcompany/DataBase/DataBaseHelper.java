package com.example.rentingcompany.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rentingcompany.Models.Property;
import com.example.rentingcompany.Models.RentingAgency;
import com.example.rentingcompany.Models.Tenant;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE SIGNIN(EMAIL TEXT PRIMARY KEY,PASSWORD TEXT,STATUS TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE RENTINGAGENCY(EMAIL TEXT PRIMARY KEY,AGENCYNAME TEXT,PASSWORD TEXT,CONFIRMPASSWORD TEXT,COUNTRY TEXT,CITY TEXT,PHONENUMBER TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE TENANT(EMAIL TEXT PRIMARY KEY,FIRSTNAME TEXT,LASTNAME TEXT,GENDER TEXT,PASSWORD TEXT,CONFIRMPASSWORD TEXT,NATIONALITY TEXT,GROSSMONTHLYSALARY TEXT,OCCUPATION TEXT,FAMILYSIZE TEXT,CURRENTRESIDENCECOUNTRY TEXT,CITY TEXT,PHONENUMBER TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE PROPERTY(POSTALADDRESS TEXT PRIMARY KEY,CITY TEXT,SURFACEAREA INTEGER,CONSTRUCTIONYEAR INTEGER,NUMOFBEDROOMS INTEGER,RENTALPRICE DOUBLE,ISFURNISHED BOOLEAN,PHOTOURL TEXT,AVAILABILTYDATE DATE,DESCRIPTION TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE CONTRACT(POSTALADDRESS TEXT, EMAIL TEXT, PRIMARY KEY(POSTALADDRESS, EMAIL), foreign key (POSTALADDRESS) references PROPERTY(POSTALADDRESS), foreign key (EMAIL) references TENANT(EMAIL))");
        sqLiteDatabase.execSQL("CREATE TABLE HAVE(POSTALADDRESS TEXT, EMAIL TEXT, PRIMARY KEY(POSTALADDRESS, EMAIL), foreign key (POSTALADDRESS) references PROPERTY(POSTALADDRESS), foreign key (EMAIL) references RENTINGAGENCY(EMAIL))");

        //Initial values (email & password)
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", "abd@gmail.com");
        contentValues.put("PASSWORD", SHA.encryptSHA512("user"));
        contentValues.put("STATUS", "Tenant");

        sqLiteDatabase.insert("SIGNIN", null, contentValues);
        /*


        sqLiteDatabase.insert("SIGNIN", null, contentValues);
*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertHave(String pData, String rData) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("POSTALADDRESS", pData);
        contentValues.put("EMAIL", rData);
        sqLiteDatabase.insert("HAVE", null, contentValues);

        sqLiteDatabase.close();
    }

    public void insertContract(String pData, String tData) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("POSTALADDRESS", pData);
        contentValues.put("EMAIL", tData);
        sqLiteDatabase.insert("CONTRACT", null, contentValues);

        sqLiteDatabase.close();
    }

    public void insertProperty(Property pData) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("POSTALADDRESS", pData.getPostalAddress());
        contentValues.put("CITY", pData.getCity());
        contentValues.put("SURFACEAREA", pData.getSurfaceArea());
        contentValues.put("CONSTRUCTIONYEAR", pData.getConstructionYear());
        contentValues.put("NUMOFBEDROOMS", pData.getNumOfBedrooms());
        contentValues.put("RENTALPRICE", pData.getRentalPrice());
        contentValues.put("ISFURNISHED", (pData.isFurnished()? "TRUE":"FALSE"));
        contentValues.put("PHOTOURL", pData.getPhotoURL());
        contentValues.put("AVAILABILTYDATE", pData.getAvailabilityDate());
        contentValues.put("DESCRIPTION", pData.getDescryption());
        sqLiteDatabase.insert("PROPERTY", null, contentValues);

        sqLiteDatabase.close();
    }

    public void deleteAllProperties(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("delete from PROPERTY");
    }


    public void insertRentingAgency(RentingAgency rData) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", rData.getEmailAddress());
        contentValues.put("AGENCYNAME", rData.getAgencyName());
        contentValues.put("PASSWORD", rData.getPassword());
        contentValues.put("CONFIRMPASSWORD", rData.getConfirmPassword());
        contentValues.put("COUNTRY", rData.getCountry());
        contentValues.put("CITY", rData.getCity());
        contentValues.put("PHONENUMBER", rData.getPhoneNumber());
        sqLiteDatabase.insert("RENTINGAGENCY", null, contentValues);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("EMAIL", rData.getEmailAddress());
        contentValues2.put("PASSWORD", rData.getPassword());
        contentValues2.put("STATUS", "RENTINGAGENCY");
        sqLiteDatabase.insert("SIGNIN", null, contentValues2);

        sqLiteDatabase.close();

    }

    public void insertTenant(Tenant tData) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", tData.getEmailAddress());
        contentValues.put("FIRSTNAME", tData.getFirstName());
        contentValues.put("LASTNAME", tData.getLastName());
        contentValues.put("GENDER", tData.getGender());
        contentValues.put("PASSWORD", tData.getPassword());
        contentValues.put("CONFIRMPASSWORD", tData.getConfirmPassword());
        contentValues.put("NATIONALITY", tData.getNationality());
        contentValues.put("GROSSMONTHLYSALARY", tData.getGrossMonthlySalary());
        contentValues.put("OCCUPATION", tData.getOccupation());
        contentValues.put("FAMILYSIZE", tData.getFamilySize());
        contentValues.put("CURRENTRESIDENCECOUNTRY", tData.getCurrentResidenceCountry());
        contentValues.put("CITY", tData.getCity());
        contentValues.put("PHONENUMBER", tData.getPhoneNumber());
        sqLiteDatabase.insert("TENANT", null, contentValues);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("EMAIL", tData.getEmailAddress());
        contentValues2.put("PASSWORD", tData.getPassword());
        contentValues2.put("STATUS", "TENANT");
        sqLiteDatabase.insert("SIGNIN", null, contentValues2);

        sqLiteDatabase.close();
    }


    public void updateTenant(Tenant tData , String emailAddress) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME", tData.getFirstName());
        contentValues.put("LASTNAME", tData.getLastName());
        contentValues.put("GENDER", tData.getGender());
        contentValues.put("PASSWORD", tData.getPassword());
        contentValues.put("CONFIRMPASSWORD", tData.getConfirmPassword());
        contentValues.put("NATIONALITY", tData.getNationality());
        contentValues.put("GROSSMONTHLYSALARY", tData.getGrossMonthlySalary());
        contentValues.put("OCCUPATION", tData.getOccupation());
        contentValues.put("FAMILYSIZE", tData.getFamilySize());
        contentValues.put("CURRENTRESIDENCECOUNTRY", tData.getCurrentResidenceCountry());
        contentValues.put("CITY", tData.getCity());
        contentValues.put("PHONENUMBER", tData.getPhoneNumber());
        sqLiteDatabase.update("TENANT", contentValues, "EMAIL = ? ",new String[]{ emailAddress });

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("PASSWORD", tData.getPassword());
        contentValues2.put("STATUS", "TENANT");
        sqLiteDatabase.update("SIGNIN", contentValues2, "EMAIL = ? ",new String[]{ emailAddress });

        sqLiteDatabase.close();

    }


    public void updateRentingAgency(RentingAgency rData , String emailAddress){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("AGENCYNAME", rData.getAgencyName());
        contentValues.put("PASSWORD", rData.getPassword());
        contentValues.put("CONFIRMPASSWORD", rData.getConfirmPassword());
        contentValues.put("COUNTRY", rData.getCountry());
        contentValues.put("CITY", rData.getCity());
        contentValues.put("PHONENUMBER", rData.getPhoneNumber());
        sqLiteDatabase.update("RENTINGAGENCY", contentValues, "EMAIL = ? ",new String[]{ emailAddress });

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("PASSWORD", rData.getPassword());
        contentValues2.put("STATUS", "RENTINGAGENCY");

        sqLiteDatabase.update("SIGNIN", contentValues2, "EMAIL = ? ",new String[]{ emailAddress });

        sqLiteDatabase.close();
    }

    public void updateProperty(Property pData , String postalAddress){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CITY", pData.getCity());
        contentValues.put("SURFACEAREA", pData.getSurfaceArea());
        contentValues.put("CONSTRUCTIONYEAR", pData.getConstructionYear());
        contentValues.put("NUMOFBEDROOMS", pData.getNumOfBedrooms());
        contentValues.put("RENTALPRICE", pData.getRentalPrice());
        contentValues.put("ISFURNISHED", (pData.isFurnished()? "TRUE":"FALSE"));
        contentValues.put("PHOTOURL", pData.getPhotoURL());
        contentValues.put("AVAILABILTYDATE", pData.getAvailabilityDate());
        contentValues.put("DESCRIPTION", pData.getDescryption());
        sqLiteDatabase.update("PROPERTY", contentValues, "POSTALADDRESS = ? ",new String[]{ postalAddress });

        sqLiteDatabase.close();
    }
    public Cursor getAllSignInData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM SIGNIN", null);
    }

    public Cursor getAllHaveData(String EmailRentingAgency) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM HAVE WHERE EMAIL LIKE '" + EmailRentingAgency + "'", null);
    }

    public Cursor getAllContractDataForTenant(String EmailTenant) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM CONTRACT WHERE EMAIL LIKE '" + EmailTenant + "'", null);
    }

    public Cursor getAllContractDataForRentingAgency(String EmailRentingAgency) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM CONTRACT C, HAVE H WHERE C.POSTALADDRESS = H.POSTALADDRESS AND H.EMAIL LIKE '" + EmailRentingAgency + "'", null);
    }

    public Cursor getRentingAgencyData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM RENTINGAGENCY", null);
    }

    public Cursor getTenantData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM TENANT", null);
    }

    public Cursor getPropertyData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM PROPERTY", null);
    }

    public Cursor getStatus(String Email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("Select STATUS from SIGNIN WHERE EMAIL LIKE '" + Email + "'", null);
    }

}
