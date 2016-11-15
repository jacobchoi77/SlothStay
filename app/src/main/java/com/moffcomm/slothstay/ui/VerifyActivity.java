package com.moffcomm.slothstay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.SlothStayApplication;
import com.moffcomm.slothstay.model.Reservation;
import com.moffcomm.slothstay.model.User;
import com.moffcomm.slothstay.util.Utils;

import java.util.Date;

public class VerifyActivity extends AppCompatActivity {

    private Reservation reservation;
    private User user;

    private TextView verifyCheckTextView;
    private TextView verifyCheckDescTextView;
    private View cardLinearLayout;

    private EditText editText;
    private Button okButton;

    private ImageView checkInImageView;
    private ImageView checkOutImageView;

    boolean isOpened = false;
    private boolean isCheckInStep = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        user = ((SlothStayApplication) getApplication()).getUser();
        if (user == null) {
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        reservation = getIntent().getParcelableExtra("reservation");

        verifyCheckTextView = (TextView) findViewById(R.id.verify_check);
        verifyCheckDescTextView = (TextView) findViewById(R.id.verify_check_desc);
        cardLinearLayout = findViewById(R.id.cardLinearLayout);

        checkInImageView = (ImageView) findViewById(R.id.checkInImageView);
        checkOutImageView = (ImageView) findViewById(R.id.checkOutImageView);

        editText = (EditText) findViewById(R.id.editText);
        okButton = (Button) findViewById(R.id.okButton);

//        setListenerToRootView();

        ((TextView) findViewById(R.id.checkInTextView))
                .setText(Utils.getDateString(reservation.getCheckInDate(), getString(R.string.hotel_date_time_format)));
        ((TextView) findViewById(R.id.checkOutTextView))
                .setText(Utils.getDateString(reservation.getCheckOutDate(), getString(R.string.hotel_date_time_format)));
        Date checkInVerifiedDate = reservation.getCheckInVerifiedDate();
        if (checkInVerifiedDate != null) {
            isCheckInStep = false;
            checkInImageView.setImageResource(R.drawable.img_stamp_on);
            ((TextView) findViewById(R.id.checkInVerifyTextView))
                    .setText(Utils.getDateString(reservation.getCheckInVerifiedDate(), getString(R.string.hotel_date_time_format)));
            verifyCheckTextView.setText(R.string.verify_check_out);
        }
        Date checkOutVerifiedDate = reservation.getCheckOutVerifiedDate();
        if (checkOutVerifiedDate != null) {
            checkOutImageView.setImageResource(R.drawable.img_stamp_on);
            ((TextView) findViewById(R.id.checkOutVerifyTextView))
                    .setText(Utils.getDateString(reservation.getCheckOutVerifiedDate(), getString(R.string.hotel_date_time_format)));
            verifyCheckTextView.setText(R.string.verify_none);
            verifyCheckDescTextView.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            okButton.setVisibility(View.INVISIBLE);

        }

        findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText())) {
                    Toast.makeText(VerifyActivity.this, "핀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isCheckInStep) {
                    if (editText.getText().toString().equals(Constants.TEST_CHECK_IN_VERIFY)) {
                        Toast.makeText(VerifyActivity.this, "체크인 확인이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        isCheckInStep = false;
                        checkInImageView.setImageResource(R.drawable.img_stamp_on);
                        reservation.setCheckInVerifiedDate(new Date());
                        ((TextView) findViewById(R.id.checkInVerifyTextView))
                                .setText(Utils.getDateString(reservation.getCheckInVerifiedDate(), getString(R.string.hotel_date_time_format)));
                        verifyCheckTextView.setText(R.string.verify_check_out);
                        editText.setText("");
                    } else {
                        Toast.makeText(VerifyActivity.this, "체크인 확인이 실패하였습니다. 핀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (editText.getText().toString().equals(Constants.TEST_CHECK_OUT_VERIFY)) {
                        Toast.makeText(VerifyActivity.this, "체크아웃 확인이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        reservation.setCheckOutVerifiedDate(new Date());
                        checkOutImageView.setImageResource(R.drawable.img_stamp_on);
                        ((TextView) findViewById(R.id.checkOutVerifyTextView))
                                .setText(Utils.getDateString(reservation.getCheckOutVerifiedDate(), getString(R.string.hotel_date_time_format)));
                        verifyCheckTextView.setText(R.string.verify_none);
                        verifyCheckDescTextView.setVisibility(View.GONE);
                        editText.setVisibility(View.GONE);
                        okButton.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(VerifyActivity.this, "체크아웃 확인이 실패하였습니다. 핀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("reservation", reservation);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }


}
