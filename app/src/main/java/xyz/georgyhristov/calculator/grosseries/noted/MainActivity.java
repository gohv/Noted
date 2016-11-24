package xyz.georgyhristov.calculator.grosseries.noted;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import xyz.georgyhristov.calculator.grosseries.noted.model.Note;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private String title;
    private String note;
    private String uuid;
    private ListView listView;
    private ArrayAdapter<Note> adapter;
    private ArrayList<Note> notesList;
    @BindView(R.id.noteEditText)
    EditText noteEditText;
    @BindView(R.id.titleEditText)
    EditText titleEditText;
    @OnClick(R.id.button)
    public void click(){
        title = titleEditText.getText().toString();
        note = noteEditText.getText().toString();
       // basicCRUD(realm);
        uuid = UUID.randomUUID().toString();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Note notes = realm.createObject(Note.class, "");
                notes.setId(uuid);
                notes.setTitle(title);
                notes.setNote(note);

            }
        });

        titleEditText.setText("");
        noteEditText.setText("");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
            adapter.notifyDataSetChanged();
            }
        });

    }
    @OnClick(R.id.query)
    public void query(){
        basicQuery(realm);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        notesList = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<Note>(this,android.R.layout.simple_list_item_1,notesList);
        listView.setAdapter(adapter);

        realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("note.realm")
                .build();
        Realm.deleteRealm(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }

    private void basicQuery(Realm realm) {
        RealmResults<Note> notes = realm.where(Note.class).findAll();
        for(Note t : notes){
            Log.d("realm", "realm DB : " + t.getNote());
            notesList.add(t);
        }
    }

    private void basicCRUD(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note notes = realm.createObject(Note.class, "key");
                notes.setTitle("");
                notes.setNote("");

            }
        });
    }
}
