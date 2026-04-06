package dev1503.oreuiforandroid;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import dev1503.oreui.Pixels2D;
import dev1503.oreui.StyleSheet;
import dev1503.oreui.widgets.OreButton;
import dev1503.oreui.widgets.OreSwitch;
import dev1503.oreui.widgets.OreTabs;

public class JavaDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_java_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());

        controller.hide(WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.navigationBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        OreButton btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setStyleSheet(StyleSheet.STYLE_GREEN);
        OreButton btnCreateRealms = findViewById(R.id.btnCreateRealms);
        btnCreateRealms.setStyleSheet(StyleSheet.STYLE_PURPLE);

        Pixels2D pixels2D = Pixels2D.fromText("001101100\n" +
                "011111110\n" +
                "110111011\n" +
                "110010011\n" +
                "111010111\n" +
                "011111110\n" +
                "001111100\n" +
                "000111000\n" +
                "000010000",
                '1'
        );
        OreSwitch switchHC = findViewById(R.id.switchHC);
        switchHC.setThumbIcon(pixels2D);
        StyleSheet styleSheetSwitchHC = switchHC.getThumbStyleSheet().clone();
        styleSheetSwitchHC.setTextColor(0xFF8C8D90);
        switchHC.setThumbStyleSheet(styleSheetSwitchHC);

        OreTabs tabsGm = findViewById(R.id.tabsGameMode);
        OreButton btnSurvival = new OreButton(this);
        btnSurvival.setText("生存");
        tabsGm.addButton(btnSurvival);
        OreButton btnCreative = new OreButton(this);
        btnCreative.setText("创造");
        tabsGm.addButton(btnCreative);

        OreTabs tabsDifficulty = findViewById(R.id.tabsDifficulty);
        OreButton btnPeace = new OreButton(this);
        btnPeace.setText("和平");
        tabsDifficulty.addButton(btnPeace);
        OreButton btnEasy = new OreButton(this);
        btnEasy.setText("简单");
        tabsDifficulty.addButton(btnEasy);
        OreButton btnNormal = new OreButton(this);
        btnNormal.setText("一般");
        tabsDifficulty.addButton(btnNormal);
        OreButton btnHard = new OreButton(this);
        btnHard.setText("困难");
        tabsDifficulty.addButton(btnHard);

        switchHC.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tabsGm.setEnabled(!isChecked);
            tabsDifficulty.setEnabled(!isChecked);
            if (isChecked) {
                tabsGm.setActiveIndex(0);
                tabsDifficulty.setActiveIndex(3);
            }
        });
    }
}