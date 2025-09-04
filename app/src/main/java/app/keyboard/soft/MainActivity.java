package app.keyboard.soft;

import android.view.View;

import arman.common.infocodes.InfoCode;
import arman.common.ui.DrawerActivity;

public class MainActivity extends DrawerActivity {



    @Override
    protected int[] getRequiredPermission() {
        return new int[]{InfoCode.NO_PERMISSION_REQUIRED};
    }
    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_main);
        initialize();
        onSettingsChange();
    }

    private void initialize() {
    }

    @Override
    protected void onSettingsChange() {

    }

    @Override
    public void onRotate(boolean landscape) {
        if (landscape) toast("Landscape");
        else toast("Portrait");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onClick(int id) {
        switch (id){
            case  R.id.exit: exit(); break;
        }
    }

    public void onClickKey(View v) {
        int id = v.getId();
        switch (id){
            case  R.id.exit: exit(); break;
        }
    }
}
