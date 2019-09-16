package id.atsiri.mymoviecatalogue;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.atsiri.mymoviecatalogue.db.FavoriteHelper;
import id.atsiri.mymoviecatalogue.entity.Favorite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FavoritAddUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtTitle, edtOverview;
    private Button btnSubmit;

    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;

    private Favorite favorite;
    private int position;

    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit_add_update);

        edtTitle = findViewById(R.id.edt_title);
        edtOverview = findViewById(R.id.edt_overview);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);
        if (favorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else {
            favorite = new Favorite();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            actionBarTitle = "Ubah";
            btnTitle = "Update";

            if (favorite != null) {
                edtTitle.setText(favorite.getTitle());
                edtOverview.setText(favorite.getOverView());
            }
        } else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSubmit.setText(btnTitle);
    }


    @Override
    protected void onDestroy() { super.onDestroy(); }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            String title = edtTitle.getText().toString().trim();
            String overview = edtOverview.getText().toString().trim();

            /*
            Jika fieldnya masih kosong maka tampilkan error
            */
            if (TextUtils.isEmpty(title)) {
                edtTitle.setError("Field can not be blank");
                return;
            }

            favorite.setTitle(title);
            favorite.setOverView(overview);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_FAVORITE, favorite);
            intent.putExtra(EXTRA_POSITION, position);

            /*
            Jika merupakan edit maka setresultnya UPDATE, dan jika bukan maka setresultnya ADD
            */
            if (isEdit) {
                long result = favoriteHelper.updateFavorite(favorite);
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent);
                    finish();
                } else {
                    Toast.makeText(FavoritAddUpdateActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                }
            } else {

                long result = favoriteHelper.insertFavorite(favorite);
                if (result > 0) {
                    favorite.setFavId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                } else {
                    Toast.makeText(FavoritAddUpdateActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { showAlertDialog(ALERT_DIALOG_CLOSE); }

    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    /*
    Konfirmasi dialog sebelum proses batal atau hapus
    close = 10
    deleteNote = 20
     */
    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogTitle = "Hapus Favorite";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            finish();
                        } else {
                            long result = favoriteHelper.deleteFavorite(favorite.getFavId());
                            if (result > 0) {
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_POSITION, position);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            } else {
                                Toast.makeText(FavoritAddUpdateActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }
}
