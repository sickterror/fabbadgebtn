# fabbadgebtn


Usage:
```xml
<com.timelesssoftware.fabbadge.FabBadge
android:id="@+id/fab_badge_btn"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_gravity="bottom|end"
app:fab_background_tint="#000"
app:fab_badge_background_tint="#ffbf00"
app:fab_badge_textColor="#000"
android:layout_margin="8dp"
app:fab_icon="@drawable/ic_3d_rotation_black_24dp"
app:layout_anchorGravity="bottom|end" />
```
From code:
```java
fabBadgeBtn.setFabBackgroundColor(Color.RED);
fabBadgeBtn.setFabIconTint(Color.BLACK);
fabBadgeBtn.setBadgeBackgroundColor(Color.BLACK);
fabBadgeBtn.setBadgeTextColor(Color.WHITE);
fabBadgeBtn.setFabIcon(getResources().getDrawable(R.drawable.ic_account_balance_black_24dp));
fabBadgeBtn.setBadgeNumber(10);
```
