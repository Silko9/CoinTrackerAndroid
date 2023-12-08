package ru.shapov.cointrackerandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ru.shapov.cointrackerandroid.models.Album;
import ru.shapov.cointrackerandroid.models.Coin;
import ru.shapov.cointrackerandroid.models.Dialogue;
import ru.shapov.cointrackerandroid.models.Exchange;
import ru.shapov.cointrackerandroid.models.ItemExchange;
import ru.shapov.cointrackerandroid.models.Message;
import ru.shapov.cointrackerandroid.models.RelUserCoin;
import ru.shapov.cointrackerandroid.models.User;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper instance;

    public static DataBaseHelper getInstance() {
        return instance;
    }

    private String login = "";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public DataBaseHelper(Context context) {
        super(context, "coinTrackerDB", null, 32);
        instance = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createCoinTable(db);
        createUserTable(db);
        createRelUserCoinTable(db);
        createMessages(db);
        createAlbums(db);
        createRelAlbumCoin(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS COINS");
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS USER_COIN");
        db.execSQL("DROP TABLE IF EXISTS MESSAGES");
        db.execSQL("DROP TABLE IF EXISTS ALBUMS");
        db.execSQL("DROP TABLE IF EXISTS REL_ALBUM_COIN");

        onCreate(db);
    }

    private void createCoinTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE COINS (" +
                "ID INTEGER PRIMARY KEY," +
                "NAME TEXT," +
                "DESCRIPTION TEXT," +
                "DENOMINATION INTEGER," +
                "CURRENCY TEXT," +
                "COUNTRY TEXT," +
                "MINT TEXT," +
                "YEAR_MINTING INTEGER," +
                "PICTURE_PATH TEXT)");

        db.execSQL("INSERT INTO COINS (" +
                "NAME, DESCRIPTION, DENOMINATION, CURRENCY, COUNTRY, MINT, YEAR_MINTING, PICTURE_PATH) " +
                "VALUES('Выдающиеся женщины США', 'Квотер 2023 года из серии «Выдающиеся женщины США» посвящен Йовите Идар.\n" +
                "\n" +
                "Идар выступала против образовательной и социальной дискриминации, с которой этнические мексиканцы столкнулись в Техасе. В конце концов Йовита стала журналистом. Она чувствовала, что так могла бы способствовать большему количеству социальных изменений.', " +
                "25, 'Цент', 'США', '-', 2023, 'usa1')");

        db.execSQL("INSERT INTO COINS (" +
                "NAME, DESCRIPTION, DENOMINATION, CURRENCY, COUNTRY, MINT, YEAR_MINTING, PICTURE_PATH) " +
                "VALUES('-', 'Угандийская монета 1976 года номиналом 50 центов.', 50, 'Цент', 'Уганда', '-', 1976, 'uganda1')");

        db.execSQL("INSERT INTO COINS (" +
                "NAME, DESCRIPTION, DENOMINATION, CURRENCY, COUNTRY, MINT, YEAR_MINTING, PICTURE_PATH) " +
                "VALUES('40 лет полета в космос, Гагарин', 'На аверсе монеты по центру отчеканен номинал «10 рублей», по бокам размещены пальмовая и оливковая ветви. На реверсе размещен портрет Ю.А. Гагарина в скафандре.\n" +
                "\n" +
                "Юрий Гагарин — один из самых популярных героев новейшей истории. Его 108 минут полета — навеки в истории человечества.', " +
                "10, 'Рубль', 'Россия', 'СПМД', 2001, 'russia1')");
    }

    public List<Coin> getAllCoins() {
        List<Coin> coins = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM COINS", null);
        if (cursor.moveToFirst())
            do {
                Coin coin = new Coin();
                coin.setId(Long.parseLong(cursor.getString(0)));
                coin.setName(cursor.getString(1));
                coin.setDescription(cursor.getString(2));
                coin.setDenomination(Integer.parseInt(cursor.getString(3)));
                coin.setCurrency(cursor.getString(4));
                coin.setCountry(cursor.getString(5));
                coin.setMint(cursor.getString(6));
                coin.setYearMinting(Integer.parseInt(cursor.getString(7)));
                coin.setPicturePath(cursor.getString(8));
                coins.add(coin);
            } while (cursor.moveToNext());
        return coins;
    }

    public Coin getCoinById(Long id) {
        Cursor cursor = this.getReadableDatabase().query("COINS", null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            Coin coin = new Coin();
            coin.setId(Long.parseLong(cursor.getString(0)));
            coin.setName(cursor.getString(1));
            coin.setDescription(cursor.getString(2));
            coin.setDenomination(Integer.parseInt(cursor.getString(3)));
            coin.setCurrency(cursor.getString(4));
            coin.setCountry(cursor.getString(5));
            coin.setMint(cursor.getString(6));
            coin.setYearMinting(Integer.parseInt(cursor.getString(7)));
            coin.setPicturePath(cursor.getString(8));

            cursor.close();
            return coin;
        } else {
            return null;
        }
    }

    private void createUserTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE USERS (" +
                "ID INTEGER PRIMARY KEY," +
                "LOGIN TEXT," +
                "PASSWORD TEXT)");

        db.execSQL("INSERT INTO USERS (" +
                "LOGIN, PASSWORD) " +
                "VALUES('user1', 'pass')");

        db.execSQL("INSERT INTO USERS (" +
                "LOGIN, PASSWORD) " +
                "VALUES('user2', 'pass')");

        db.execSQL("INSERT INTO USERS (" +
                "LOGIN, PASSWORD) " +
                "VALUES('user3', 'pass')");
    }

    public void createUser(String login, String password){
        this.getReadableDatabase().execSQL("INSERT INTO USERS (" +
                "LOGIN, PASSWORD) " +
                "VALUES('" + login + "','" + password + "')");
    }

    public User getUserById(Long id) {
        Cursor cursor = this.getReadableDatabase().query("USERS", null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);

        return getUser(cursor);
    }

    public User getUserByLogin(String login) {
        Cursor cursor = this.getReadableDatabase().query("USERS", null, "LOGIN=?", new String[]{login}, null, null, null);

        return getUser(cursor);
    }

    private List<Long> getAllIdUsers(){
        List<Long> idUsers = new ArrayList<>();

        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT ID FROM USERS", null);
        if (cursor.moveToFirst())
            do {
                idUsers.add(cursor.getLong(0));
            } while (cursor.moveToNext());
        return idUsers;
    }

    private User getUser(Cursor cursor){
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            User user = new User();
            user.setId(cursor.getLong(0));
            user.setLogin(cursor.getString(1));
            user.setPassword(cursor.getString(2));

            cursor.close();
            return user;
        } else {
            return null;
        }
    }

    private void createRelUserCoinTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE USER_COIN (" +
                "USER_ID INTEGER NOT NULL," +
                "COIN_ID INTEGER NOT NULL," +
                "STOCK_AMOUNT INTEGER DEFAULT 0," +
                "EXCHANGE_AMOUNT INTEGER DEFAULT 0," +
                "NEED_AMOUNT INTEGER DEFAULT 0," +
                "CONSTRAINT ID PRIMARY KEY (USER_ID, COIN_ID))");

        db.execSQL("INSERT INTO USER_COIN (" +
                "USER_ID, COIN_ID, STOCK_AMOUNT, EXCHANGE_AMOUNT) " +
                "VALUES(1, 1, 4, 3)");

        db.execSQL("INSERT INTO USER_COIN (" +
                "USER_ID, COIN_ID, STOCK_AMOUNT) " +
                "VALUES(1, 2, 1)");

        db.execSQL("INSERT INTO USER_COIN (" +
                "USER_ID, COIN_ID, NEED_AMOUNT) " +
                "VALUES(1, 3, 4)");


        db.execSQL("INSERT INTO USER_COIN (" +
                "USER_ID, COIN_ID, NEED_AMOUNT) " +
                "VALUES(2, 1, 4)");

        db.execSQL("INSERT INTO USER_COIN (" +
                "USER_ID, COIN_ID, EXCHANGE_AMOUNT) " +
                "VALUES(2, 3, 3)");

        db.execSQL("INSERT INTO USER_COIN (" +
                "USER_ID, COIN_ID, EXCHANGE_AMOUNT) " +
                "VALUES(3, 3, 2)");

    }

    public RelUserCoin getRelUserCoin(Long userId, Long coinId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = this.getReadableDatabase().query("USER_COIN", null, "USER_ID=? AND COIN_ID=?", new String[]{String.valueOf(userId), String.valueOf(coinId)}, null, null, null);

        RelUserCoin relUserCoin = getRelUserCoin(cursor);
        if(relUserCoin == null){
            db.execSQL("INSERT INTO USER_COIN (" +
                    "USER_ID, COIN_ID) " +
                    "VALUES(" + userId + "," + coinId + ")");
            cursor = this.getReadableDatabase().query("USER_COIN", null, "USER_ID=? AND COIN_ID=?", new String[]{String.valueOf(userId), String.valueOf(coinId)}, null, null, null);
            return getRelUserCoin(cursor);
        }

        return relUserCoin;
    }

    private List<RelUserCoin> getListRelUserCoin(Long userId){
        List<RelUserCoin> relUserCoins = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM USER_COIN WHERE USER_ID=?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst())
            do {
                RelUserCoin relUserCoin = new RelUserCoin();
                relUserCoin.setUserId(Long.parseLong(cursor.getString(0)));
                relUserCoin.setCoinId(Long.parseLong(cursor.getString(1)));
                relUserCoin.setStockAmount(Integer.parseInt(cursor.getString(2)));
                relUserCoin.setExchangeAmount(Integer.parseInt(cursor.getString(3)));
                relUserCoin.setNeedAmount(Integer.parseInt(cursor.getString(4)));
                relUserCoins.add(relUserCoin);
            } while (cursor.moveToNext());
        return relUserCoins;
    }

    private RelUserCoin getRelUserCoin(Cursor cursor){
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            RelUserCoin relUserCoin = new RelUserCoin();
            relUserCoin.setUserId(cursor.getLong(0));
            relUserCoin.setCoinId(cursor.getLong(1));
            relUserCoin.setStockAmount(cursor.getInt(2));
            relUserCoin.setExchangeAmount(cursor.getInt(3));
            relUserCoin.setNeedAmount(cursor.getInt(4));

            cursor.close();
            return relUserCoin;
        } else {
            return null;
        }
    }

    public void editAmountRelUserCoin(Long userId, Long coinId, int typeRel, int valueEdit){
        SQLiteDatabase db = this.getReadableDatabase();

        switch (typeRel){
            case 1:
                db.execSQL("UPDATE USER_COIN SET " +
                        "STOCK_AMOUNT =  STOCK_AMOUNT + " + valueEdit + " " +
                        "WHERE USER_ID = " + userId + " AND COIN_ID = " + coinId);
                break;
            case 2:
                db.execSQL("UPDATE USER_COIN SET " +
                        "EXCHANGE_AMOUNT =  EXCHANGE_AMOUNT + " + valueEdit + " " +
                        "WHERE USER_ID = " + userId + " AND COIN_ID = " + coinId);
                break;
            case 3:
                db.execSQL("UPDATE USER_COIN SET " +
                        "NEED_AMOUNT =  NEED_AMOUNT + " + valueEdit + " " +
                        "WHERE USER_ID = " + userId + " AND COIN_ID = " + coinId);
                break;
        }
    }

    public List<Exchange> getExchanges(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        DataBaseHelper dataBaseHelper = this;

        User user = dataBaseHelper.getUserByLogin(login);
        List<RelUserCoin> relUserCoins = dataBaseHelper.getListRelUserCoin(user.getId());
        List<Long> idUsers = dataBaseHelper.getAllIdUsers();
        idUsers.remove(user.getId());
        List<Exchange> exchanges = new ArrayList<>();
        for(Long idUser : idUsers){
            List<RelUserCoin> anotherRels = dataBaseHelper.getListRelUserCoin(idUser);
            for(RelUserCoin rel : anotherRels){
                for (RelUserCoin relMe : relUserCoins){
                    if(Objects.equals(rel.getCoinId(), relMe.getCoinId())){
                        Exchange exchange = new Exchange();
                        boolean flag = true;
                        for(Exchange e : exchanges)
                            if(Objects.equals(e.getIdUser(), idUser)) {
                                flag = false;
                                exchange = e;
                            }
                        if(flag) exchange = new Exchange();
                        if(rel.getExchangeAmount() > 0 && relMe.getNeedAmount() > 0){
                            exchange.setIdUser(idUser);
                            exchange.setHeHasForExchange(exchange.getHeHasForExchange() + Math.min(rel.getExchangeAmount(), relMe.getNeedAmount()));
                        }
                        if(rel.getNeedAmount() > 0 && relMe.getExchangeAmount() > 0){
                            exchange.setIdUser(idUser);
                            exchange.setWeHaveForExchange(exchange.getWeHaveForExchange() + Math.min(rel.getNeedAmount(), relMe.getExchangeAmount()));
                        }
                        if(Objects.equals(exchange.getIdUser(), idUser) && flag)
                            exchanges.add(exchange);
                    }
                }
            }
        }
        return exchanges;
    }

    public List<ItemExchange> getItemExchangesMe(Long idUser){
        List<ItemExchange> itemExchanges = new ArrayList<>();
        User user = getUserByLogin(getLogin());

        List<RelUserCoin> anotherRels = getListRelUserCoin(idUser);
        List<RelUserCoin> meRels = getListRelUserCoin(user.getId());
        for(RelUserCoin rel : anotherRels)
            for (RelUserCoin relMe : meRels)
                if(Objects.equals(rel.getCoinId(), relMe.getCoinId()))
                    if(relMe.getExchangeAmount() > 0 && rel.getNeedAmount() > 0)
                        itemExchanges.add(new ItemExchange(getCoinById(rel.getCoinId()), Math.min(relMe.getExchangeAmount(), rel.getNeedAmount())));

        return itemExchanges;
    }

    public List<ItemExchange> getItemExchangesHe(Long idUser){
        List<ItemExchange> itemExchanges = new ArrayList<>();
        User user = getUserByLogin(getLogin());

        List<RelUserCoin> anotherRels = getListRelUserCoin(idUser);
        List<RelUserCoin> meRels = getListRelUserCoin(user.getId());
        for(RelUserCoin rel : anotherRels)
            for (RelUserCoin relMe : meRels)
                if(Objects.equals(relMe.getCoinId(), rel.getCoinId()))
                    if(rel.getExchangeAmount() > 0 && relMe.getNeedAmount() > 0)
                        itemExchanges.add(new ItemExchange(getCoinById(rel.getCoinId()), Math.min(rel.getExchangeAmount(), relMe.getNeedAmount())));

        return itemExchanges;
    }

    private void createMessages(SQLiteDatabase db){
        db.execSQL("CREATE TABLE MESSAGES (" +
                "ID INTEGER PRIMARY KEY, " +
                "SENDER_ID INTEGER NOT NULL," +
                "RECEIVING_ID INTEGER NOT NULL," +
                "MESSAGE TEXT," +
                "DATE_CREATE TEXT)");
    }

    public void sendMessage(Long senderId, Long receivingId, String message){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());
        getReadableDatabase().execSQL("INSERT INTO MESSAGES (" +
                "SENDER_ID, RECEIVING_ID, MESSAGE, DATE_CREATE) " +
                "VALUES(" + senderId + ", " + receivingId +", '" + message + "', '" + sdf.format(new Date()) + "')");
    }

    public List<Message> getAllMessages(Long senderId, Long receivingId){
        List<Message> messages = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT SENDER_ID, MESSAGE, DATE_CREATE FROM MESSAGES WHERE (SENDER_ID=? AND RECEIVING_ID=?) OR (SENDER_ID=? AND RECEIVING_ID=?)", new String[]{String.valueOf(senderId), String.valueOf(receivingId), String.valueOf(receivingId), String.valueOf(senderId)});
        if (cursor.moveToFirst())
            do {
                Message message = new Message(cursor.getString(1), cursor.getString(2), Long.parseLong(cursor.getString(0)));
                messages.add(message);
            } while (cursor.moveToNext());
        return messages;
    }

    public List<Dialogue> getAllDialogues(){
        SQLiteDatabase db = this.getReadableDatabase();
        User user = getUserByLogin(getLogin());
        List<Dialogue> dialogues = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT SENDER_ID, RECEIVING_ID FROM MESSAGES WHERE SENDER_ID=? OR RECEIVING_ID=?", new String[]{String.valueOf(user.getId()), String.valueOf(user.getId())});
        List<String> usersId = new ArrayList<>();
        if (cursor.moveToFirst())
            do {
                if(!usersId.contains(cursor.getString(0)) && !String.valueOf(user.getId()).equals(cursor.getString(0)))
                    usersId.add(cursor.getString(0));
                if(!usersId.contains(cursor.getString(1)) && !String.valueOf(user.getId()).equals(cursor.getString(1)))
                    usersId.add(cursor.getString(1));
            } while (cursor.moveToNext());
        for(String userId : usersId){
            Dialogue dialogue = new Dialogue();
            dialogue.setUserId(Long.parseLong(userId));
            cursor = db.rawQuery("SELECT ID FROM MESSAGES WHERE (SENDER_ID=? AND RECEIVING_ID=?) OR (SENDER_ID=? AND RECEIVING_ID=?)", new String[]{String.valueOf(user.getId()), userId, userId, String.valueOf(user.getId())});
            if(cursor.moveToFirst())
                dialogue.setCountMessages(cursor.getCount());
            cursor = db.rawQuery("SELECT DATE_CREATE FROM MESSAGES WHERE (SENDER_ID=? AND RECEIVING_ID=?) OR (SENDER_ID=? AND RECEIVING_ID=?)", new String[]{String.valueOf(user.getId()), userId, userId, String.valueOf(user.getId())});
            if(cursor.moveToLast())
                dialogue.setLastDateMessage(cursor.getString(0));
            dialogues.add(dialogue);
        }

        return dialogues;
    }

    private void createAlbums(SQLiteDatabase db){
        db.execSQL("CREATE TABLE ALBUMS (" +
                "ID INTEGER PRIMARY KEY," +
                "USER_ID INTEGER NOT NULL," +
                "NAME TEXT NOT NULL," +
                "DATE_CREATE TEXT NOT NULL)");

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        db.execSQL("INSERT INTO ALBUMS (" +
                "USER_ID, NAME, DATE_CREATE) " +
                "VALUES(1, 'Монетки', '" + sdf.format(new Date()) + "')");
    }

    public List<Album> getAllAlbum(){
        List<Album> albums = new ArrayList<>();
        User user = getUserByLogin(getLogin());

        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM ALBUMS WHERE USER_ID=?", new String[]{String.valueOf(user.getId())});
        if (cursor.moveToFirst())
            do {
                Album album = new Album();
                album.setId(Long.parseLong(cursor.getString(0)));
                album.setUserId(Long.parseLong(cursor.getString(1)));
                album.setName(cursor.getString(2));
                album.setDateCreate(cursor.getString(3));
                albums.add(album);
            } while (cursor.moveToNext());

        return albums;
    }

    public Album getAlbumById(Long albumId){
        Cursor cursor = this.getReadableDatabase().query("ALBUMS", null, "ID=?", new String[]{String.valueOf(albumId)}, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            Album album = new Album();
            album.setId(cursor.getLong(0));
            album.setUserId(cursor.getLong(1));
            album.setName(cursor.getString(2));
            album.setDateCreate(cursor.getString(3));

            cursor.close();
            return album;
        } else {
            return null;
        }
    }

    public void createAlbum(String name){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        User user = getUserByLogin(getLogin());
        this.getReadableDatabase().execSQL("INSERT INTO ALBUMS (" +
                "USER_ID, NAME, DATE_CREATE) " +
                "VALUES(" + user.getId() + ", '" + name + "', '" + sdf.format(new Date()) + "')");
    }

    public void deleteAlbumById(Long albumId){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM ALBUMS WHERE ID=?", new String[]{String.valueOf(albumId)});
        db.execSQL("DELETE FROM REL_ALBUM_COIN WHERE ALBUM_ID=?", new String[]{String.valueOf(albumId)});
    }

    private void createRelAlbumCoin(SQLiteDatabase db){
        db.execSQL("CREATE TABLE REL_ALBUM_COIN (" +
                "ALBUM_ID INTEGER NOT NULL," +
                "COIN_ID INTEGER NOT NULL," +
                "CONSTRAINT ID PRIMARY KEY (ALBUM_ID, COIN_ID))");

        db.execSQL("INSERT INTO REL_ALBUM_COIN (" +
                "ALBUM_ID, COIN_ID) " +
                "VALUES(1, 1)");

        db.execSQL("INSERT INTO REL_ALBUM_COIN (" +
                "ALBUM_ID, COIN_ID) " +
                "VALUES(1, 3)");
    }

    public void addCoinToAlbum(Long coinId, Long albumId){
        this.getReadableDatabase().execSQL("INSERT INTO REL_ALBUM_COIN (" +
                "ALBUM_ID, COIN_ID) " +
                "VALUES(" + albumId + ", " + coinId +")");
    }

    public void removeCoinToAlbum(Long coinId, Long albumId){
        this.getReadableDatabase().execSQL("DELETE FROM REL_ALBUM_COIN WHERE ALBUM_ID=? AND COIN_ID=?", new String[]{String.valueOf(albumId), String.valueOf(coinId)});
    }

    public List<Coin> getAllCoinByAlbum(Long albumId){
        List<Coin> coins = new ArrayList<>();
        DataBaseHelper db = DataBaseHelper.getInstance();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT COIN_ID FROM REL_ALBUM_COIN WHERE ALBUM_ID=?", new String[]{String.valueOf(albumId)});
        if (cursor.moveToFirst())
            do {
                coins.add(db.getCoinById(Long.parseLong(cursor.getString(0))));
            } while (cursor.moveToNext());

        return coins;
    }

    public List<Coin> getAllCoinNoTheAlbum(Long albumId){
        List<Coin> coins = new ArrayList<>();
        DataBaseHelper db = DataBaseHelper.getInstance();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT ID FROM COINS", new String[]{});
        if (cursor.moveToFirst())
            do {
                Cursor cursorHelp = this.getReadableDatabase().rawQuery("SELECT COIN_ID FROM REL_ALBUM_COIN WHERE ALBUM_ID=? AND COIN_ID=?", new String[]{String.valueOf(albumId), String.valueOf(cursor.getString(0))});
                if(cursorHelp.getCount() == 0)
                    coins.add(db.getCoinById(Long.parseLong(cursor.getString(0))));
            } while (cursor.moveToNext());

        return coins;
    }

    public int getCountCoinByAlbum(Long albumId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COIN_ID FROM REL_ALBUM_COIN WHERE ALBUM_ID=?", new String[]{String.valueOf(albumId)});
        if(cursor.moveToFirst())
            return cursor.getCount();
        return 0;
    }
}