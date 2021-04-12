package com.digivalet.brandresidential.ui.views.countrypicker;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.ui.views.countrypicker.listeners.BottomSheetInteractionListener;
import com.digivalet.brandresidential.ui.views.countrypicker.listeners.OnCountryPickerListener;
import com.digivalet.brandresidential.ui.views.countrypicker.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

//TODO have to set dynamic theme
public class CountryPicker implements BottomSheetInteractionListener, LifecycleObserver {

    // region Countries
    private final Country[] COUNTRIES = {
            new Country("AD", "Andorra", "+376", "EUR"),
            new Country("AE", "United Arab Emirates", "+971", "AED"),
            new Country("AF", "Afghanistan", "+93", "AFN"),
            new Country("AG", "Antigua and Barbuda", "+1-268", "XCD"),
            new Country("AI", "Anguilla", "+1-264", "XCD"),
            new Country("AL", "Albania", "+355", "ALL"),
            new Country("AM", "Armenia", "+374", "AMD"),
            new Country("AN", "Netherlands Antilles", "+599", "ANG"),
            new Country("AO", "Angola", "+244", "AOA"),
            new Country("AQ", "Antarctica", "+672", "USD"),
            new Country("AR", "Argentina", "+54", "ARS"),
            new Country("AS", "American Samoa", "+1-684", "USD"),
            new Country("AT", "Austria", "+43", "EUR"),
            new Country("AU", "Australia", "+61", "AUD"),
            new Country("AW", "Aruba", "+297", "AWG"),
            new Country("AZ", "Azerbaijan", "+994", "AZN"),
            new Country("BA", "Bosnia and Herzegovina", "+387", "BAM"),
            new Country("BB", "Barbados", "+1-246", "BBD"),
            new Country("BD", "Bangladesh", "+880", "BDT"),
            new Country("BE", "Belgium", "+32", "EUR"),
            new Country("BF", "Burkina Faso", "+226", "XOF"),
            new Country("BG", "Bulgaria", "+359", "BGN"),
            new Country("BH", "Bahrain", "+973", "BHD"),
            new Country("BI", "Burundi", "+257", "BIF"),
            new Country("BJ", "Benin", "+229", "XOF"),
            new Country("BL", "Saint Barthelemy", "+590", "EUR"),
            new Country("BM", "Bermuda", "+1-441", "BMD"),
            new Country("BN", "Brunei", "+673", "BND"),
            new Country("BO", "Bolivia", "+591", "BOB"),
            new Country("BR", "Brazil", "+55", "BRL"),
            new Country("BS", "Bahamas", "+1-242", "BSD"),
            new Country("BT", "Bhutan", "+975", "BTN"),
            new Country("BW", "Botswana", "+267", "BWP"),
            new Country("BY", "Belarus", "+375", "BYR"),
            new Country("BZ", "Belize", "+501", "BZD"),
            new Country("CA", "Canada", "+1", "CAD"),
            new Country("CC", "Cocos Islands", "+61", "AUD"),
            new Country("CD", "Democratic Republic of the Congo", "+243", "CDF"),
            new Country("CF", "Central African Republic", "+236", "XAF"),
            new Country("CG", "Republic of the Congo", "+242", "XAF"),
            new Country("CH", "Switzerland", "+41", "CHF"),
            new Country("CI", "Ivory Coast", "+225", "XOF"),
            new Country("CK", "Cook Islands", "+682", "NZD"),
            new Country("CL", "Chile", "+56", "CLP"),
            new Country("CM", "Cameroon", "+237", "XAF"),
            new Country("CN", "China", "+86", "CNY"),
            new Country("CO", "Colombia", "+57", "COP"),
            new Country("CR", "Costa Rica", "+506", "CRC"),
            new Country("CU", "Cuba", "+53", "CUP"),
            new Country("CV", "Cape Verde", "+238", "CVE"),
            new Country("CW", "Curacao", "+599", "ANG"),
            new Country("CX", "Christmas Island", "+61", "AUD"),
            new Country("CY", "Cyprus", "+357", "EUR"),
            new Country("CZ", "Czech Republic", "+420", "CZK"),
            new Country("DE", "Germany", "+49", "EUR"),
            new Country("DJ", "Djibouti", "+253", "DJF"),
            new Country("DK", "Denmark", "+45", "DKK"),
            new Country("DM", "Dominica", "+1-767", "XCD"),
            new Country("DO", "Dominican Republic", "+1-809, +1-829, +1-849", "DOP"),
            new Country("DZ", "Algeria", "+213", "DZD"),
            new Country("EC", "Ecuador", "+593", "USD"),
            new Country("EE", "Estonia", "+372", "EUR"),
            new Country("EG", "Egypt", "+20", "EGP"),
            new Country("EH", "Western Sahara", "+212", "MAD"),
            new Country("ER", "Eritrea", "+291", "ERN"),
            new Country("ES", "Spain", "+34", "EUR"),
            new Country("ET", "Ethiopia", "+251", "ETB"),
            new Country("FI", "Finland", "+358", "EUR"),
            new Country("FJ", "Fiji", "+679", "FJD"),
            new Country("FK", "Falkland Islands", "+500", "FKP"),
            new Country("FM", "Micronesia", "+691", "USD"),
            new Country("FO", "Faroe Islands", "+298", "DKK"),
            new Country("FR", "France", "+33", "EUR"),
            new Country("GA", "Gabon", "+241", "XAF"),
            new Country("GB", "United Kingdom", "+44", "GBP"),
            new Country("GD", "Grenada", "+1-473", "XCD"),
            new Country("GE", "Georgia", "+995", "GEL"),
            new Country("GG", "Guernsey", "+44-1481", "GGP"),
            new Country("GH", "Ghana", "+233", "GHS"),
            new Country("GI", "Gibraltar", "+350", "GIP"),
            new Country("GL", "Greenland", "+299", "DKK"),
            new Country("GM", "Gambia", "+220", "GMD"),
            new Country("GN", "Guinea", "+224", "GNF"),
            new Country("GQ", "Equatorial Guinea", "+240", "XAF"),
            new Country("GR", "Greece", "+30", "EUR"),
            new Country("GT", "Guatemala", "+502", "GTQ"),
            new Country("GU", "Guam", "+1-671", "USD"),
            new Country("GW", "Guinea-Bissau", "+245", "XOF"),
            new Country("GY", "Guyana", "+592", "GYD"),
            new Country("HK", "Hong Kong", "+852", "HKD"),
            new Country("HN", "Honduras", "+504", "HNL"),
            new Country("HR", "Croatia", "+385", "HRK"),
            new Country("HT", "Haiti", "+509", "HTG"),
            new Country("HU", "Hungary", "+36", "HUF"),
            new Country("ID", "Indonesia", "+62", "IDR"),
            new Country("IE", "Ireland", "+353", "EUR"),
            new Country("IL", "Israel", "+972", "ILS"),
            new Country("IM", "Isle of Man", "+44-1624", "GBP"),
            new Country("IN", "India", "+91", "INR"),
            new Country("IO", "British Indian Ocean Territory", "+246", "USD"),
            new Country("IQ", "Iraq", "+964", "IQD"),
            new Country("IR", "Iran", "+98", "IRR"),
            new Country("IS", "Iceland", "+354", "ISK"),
            new Country("IT", "Italy", "+39", "EUR"),
            new Country("JE", "Jersey", "+44-1534", "JEP"),
            new Country("JM", "Jamaica", "+1-876", "JMD"),
            new Country("JO", "Jordan", "+962", "JOD"),
            new Country("JP", "Japan", "+81", "JPY"),
            new Country("KE", "Kenya", "+254", "KES"),
            new Country("KG", "Kyrgyzstan", "+996", "KGS"),
            new Country("KH", "Cambodia", "+855", "KHR"),
            new Country("KI", "Kiribati", "+686", "AUD"),
            new Country("KM", "Comoros", "+269", "KMF"),
            new Country("KN", "Saint Kitts and Nevis", "+1-869", "XCD"),
            new Country("KP", "North Korea", "+850", "KPW"),
            new Country("KR", "South Korea", "+82", "KRW"),
            new Country("KW", "Kuwait", "+965", "KWD"),
            new Country("KY", "Cayman Islands", "+1-345", "KYD"),
            new Country("KZ", "Kazakhstan", "+7", "KZT"),
            new Country("LA", "Laos", "+856", "LAK"),
            new Country("LB", "Lebanon", "+961", "LBP"),
            new Country("LC", "Saint Lucia", "+1-758", "XCD"),
            new Country("LI", "Liechtenstein", "+423", "CHF"),
            new Country("LK", "Sri Lanka", "+94", "LKR"),
            new Country("LR", "Liberia", "+231", "LRD"),
            new Country("LS", "Lesotho", "+266", "LSL"),
            new Country("LT", "Lithuania", "+370", "LTL"),
            new Country("LU", "Luxembourg", "+352", "EUR"),
            new Country("LV", "Latvia", "+371", "LVL"),
            new Country("LY", "Libya", "+218", "LYD"),
            new Country("MA", "Morocco", "+212", "MAD"),
            new Country("MC", "Monaco", "+377", "EUR"),
            new Country("MD", "Moldova", "+373", "MDL"),
            new Country("ME", "Montenegro", "+382", "EUR"),
            new Country("MF", "Saint Martin", "+590", "EUR"),
            new Country("MG", "Madagascar", "+261", "MGA"),
            new Country("MH", "Marshall Islands", "+692", "USD"),
            new Country("MK", "Macedonia", "+389", "MKD"),
            new Country("ML", "Mali", "+223", "XOF"),
            new Country("MM", "Myanmar", "+95", "MMK"),
            new Country("MN", "Mongolia", "+976", "MNT"),
            new Country("MO", "Macao", "+853", "MOP"),
            new Country("MP", "Northern Mariana Islands", "+1-670", "USD"),
            new Country("MR", "Mauritania", "+222", "MRO"),
            new Country("MS", "Montserrat", "+1-664", "XCD"),
            new Country("MT", "Malta", "+356", "EUR"),
            new Country("MU", "Mauritius", "+230", "MUR"),
            new Country("MV", "Maldives", "+960", "MVR"),
            new Country("MW", "Malawi", "+265", "MWK"),
            new Country("MX", "Mexico", "+52", "MXN"),
            new Country("MY", "Malaysia", "+60", "MYR"),
            new Country("MZ", "Mozambique", "+258", "MZN"),
            new Country("NA", "Namibia", "+264", "NAD"),
            new Country("NC", "New Caledonia", "+687", "XPF"),
            new Country("NE", "Niger", "+227", "XOF"),
            new Country("NG", "Nigeria", "+234", "NGN"),
            new Country("NI", "Nicaragua", "+505", "NIO"),
            new Country("NL", "Netherlands", "+31", "EUR"),
            new Country("NO", "Norway", "+47", "NOK"),
            new Country("NP", "Nepal", "+977", "NPR"),
            new Country("NR", "Nauru", "+674", "AUD"),
            new Country("NU", "Niue", "+683", "NZD"),
            new Country("NZ", "New Zealand", "+64", "NZD"),
            new Country("OM", "Oman", "+968", "OMR"),
            new Country("PA", "Panama", "+507", "PAB"),
            new Country("PE", "Peru", "+51", "PEN"),
            new Country("PF", "French Polynesia", "+689", "XPF"),
            new Country("PG", "Papua New Guinea", "+675", "PGK"),
            new Country("PH", "Philippines", "+63", "PHP"),
            new Country("PK", "Pakistan", "+92", "PKR"),
            new Country("PL", "Poland", "+48", "PLN"),
            new Country("PM", "Saint Pierre and Miquelon", "+508", "EUR"),
            new Country("PN", "Pitcairn", "+64", "NZD"),
            new Country("PR", "Puerto Rico", "+1-787, +1-939", "USD"),
            new Country("PS", "Palestinian", "+970", "ILS"),
            new Country("PT", "Portugal", "+351", "EUR"),
            new Country("PW", "Palau", "+680", "USD"),
            new Country("PY", "Paraguay", "+595", "PYG"),
            new Country("QA", "Qatar", "+974", "QAR"),
            new Country("RE", "Reunion", "+262", "EUR"),
            new Country("RO", "Romania", "+40", "RON"),
            new Country("RS", "Serbia", "+381", "RSD"),
            new Country("RU", "Russia", "+7", "RUB"),
            new Country("RW", "Rwanda", "+250", "RWF"),
            new Country("SA", "Saudi Arabia", "+966", "SAR"),
            new Country("SB", "Solomon Islands", "+677", "SBD"),
            new Country("SC", "Seychelles", "+248", "SCR"),
            new Country("SD", "Sudan", "+249", "SDG"),
            new Country("SE", "Sweden", "+46", "SEK"),
            new Country("SG", "Singapore", "+65", "SGD"),
            new Country("SH", "Saint Helena", "+290", "SHP"),
            new Country("SI", "Slovenia", "+386", "EUR"),
            new Country("SJ", "Svalbard and Jan Mayen", "+47", "NOK"),
            new Country("SK", "Slovakia", "+421", "EUR"),
            new Country("SL", "Sierra Leone", "+232", "SLL"),
            new Country("SM", "San Marino", "+378", "EUR"),
            new Country("SN", "Senegal", "+221", "XOF"),
            new Country("SO", "Somalia", "+252", "SOS"),
            new Country("SR", "Suriname", "+597", "SRD"),
            new Country("SS", "South Sudan", "+211", "SSP"),
            new Country("ST", "Sao Tome and Principe", "+239", "STD"),
            new Country("SV", "El Salvador", "+503", "SVC"),
            new Country("SX", "Sint Maarten", "+1-721", "ANG"),
            new Country("SY", "Syria", "+963", "SYP"),
            new Country("SZ", "Swaziland", "+268", "SZL"),
            new Country("TC", "Turks and Caicos Islands", "+1-649", "USD"),
            new Country("TD", "Chad", "+235", "XAF"),
            new Country("TG", "Togo", "+228", "XOF"),
            new Country("TH", "Thailand", "+66", "THB"),
            new Country("TJ", "Tajikistan", "+992", "TJS"),
            new Country("TK", "Tokelau", "+690", "NZD"),
            new Country("TL", "East Timor", "+670", "USD"),
            new Country("TM", "Turkmenistan", "+993", "TMT"),
            new Country("TN", "Tunisia", "+216", "TND"),
            new Country("TO", "Tonga", "+676", "TOP"),
            new Country("TR", "Turkey", "+90", "TRY"),
            new Country("TT", "Trinidad and Tobago", "+1-868", "TTD"),
            new Country("TV", "Tuvalu", "+688", "AUD"),
            new Country("TW", "Taiwan", "+886", "TWD"),
            new Country("TZ", "Tanzania", "+255", "TZS"),
            new Country("UA", "Ukraine", "+380", "UAH"),
            new Country("UG", "Uganda", "+256", "UGX"),
            new Country("US", "United States", "+1", "USD"),
            new Country("UY", "Uruguay", "+598", "UYU"),
            new Country("UZ", "Uzbekistan", "+998", "UZS"),
            new Country("VA", "Vatican", "+379", "EUR"),
            new Country("VC", "Saint Vincent and the Grenadines", "+1-784", "XCD"),
            new Country("VE", "Venezuela", "+58", "VEF"),
            new Country("VG", "British Virgin Islands", "+1-284", "USD"),
            new Country("VI", "U.S. Virgin Islands", "+1-340", "USD"),
            new Country("VN", "Vietnam", "+84", "VND"),
            new Country("VU", "Vanuatu", "+678", "VUV"),
            new Country("WF", "Wallis and Futuna", "+681", "XPF"),
            new Country("WS", "Samoa", "+685", "WST"),
            new Country("XK", "Kosovo", "+383", "EUR"),
            new Country("YE", "Yemen", "+967", "YER"),
            new Country("YT", "Mayotte", "+262", "EUR"),
            new Country("ZA", "South Africa", "+27", "ZAR"),
            new Country("ZM", "Zambia", "+260", "ZMW"),
            new Country("ZW", "Zimbabwe", "+263", "USD"),
    };

    // region Variables
    public static final int SORT_BY_NONE = 0;
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_ISO = 2;
    public static final int SORT_BY_DIAL_CODE = 3;
    public static final int THEME_OLD = 1;
    public static final int THEME_NEW = 2;
    private int theme;

    private int style;
    private Context context;
    private int sortBy = SORT_BY_NONE;
    private OnCountryPickerListener onCountryPickerListener;
    private boolean canSearch = true;

    private List<Country> countries;
    private EditText searchEditText;
    private RecyclerView countriesRecyclerView;
    private LinearLayout rootView;
    private int textColor;
    private int hintColor;
    private int backgroundColor;
    private int searchIconId;
    private Drawable searchIcon;
    private CountriesAdapter adapter;
    private List<Country> searchResults;
    private BottomSheetDialogView bottomSheetDialog;
    private Dialog dialog;
    // endregion

    // region Constructors
    private CountryPicker() {
    }

    CountryPicker(Builder builder) {
        sortBy = builder.sortBy;
        if (builder.onCountryPickerListener != null) {
            onCountryPickerListener = builder.onCountryPickerListener;
        }
        style = builder.style;
        context = builder.context;
        canSearch = builder.canSearch;
        theme = builder.theme;
        countries = new ArrayList<>(Arrays.asList(COUNTRIES));
        sortCountries(countries);
    }
    // endregion

    // region Listeners
    private void sortCountries(@NonNull List<Country> countries) {
        if (sortBy == SORT_BY_NAME) {
            Collections.sort(countries, new Comparator<Country>() {
                @Override
                public int compare(Country country1, Country country2) {
                    return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());
                }
            });
        } else if (sortBy == SORT_BY_ISO) {
            Collections.sort(countries, new Comparator<Country>() {
                @Override
                public int compare(Country country1, Country country2) {
                    return country1.getCode().trim().compareToIgnoreCase(country2.getCode().trim());
                }
            });
        } else if (sortBy == SORT_BY_DIAL_CODE) {
            Collections.sort(countries, new Comparator<Country>() {
                @Override
                public int compare(Country country1, Country country2) {
                    return country1.getDialCode().trim().compareToIgnoreCase(country2.getDialCode().trim());
                }
            });
        }
    }
    // endregion

    // region Utility Methods
    public void showDialog(@NonNull AppCompatActivity activity) {
        if (countries == null || countries.isEmpty()) {
            throw new IllegalArgumentException(context.getString(R.string.error_no_countries_found));
        } else {
            activity.getLifecycle().addObserver(this);
            dialog = new Dialog(activity);
            View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_country_picker, null);
            initiateUi(dialogView);
            setCustomStyle(dialogView);
            setSearchEditText();
            setupRecyclerView(dialogView);
            dialog.setContentView(dialogView);
            if (dialog.getWindow() != null) {
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(params);
                if (theme == THEME_NEW) {
                    Drawable background =
                            ContextCompat.getDrawable(context, R.drawable.ic_dialog_new_background);
                    if (background != null) {
                        background.setColorFilter(
                                new PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP));
                    }
                    rootView.setBackgroundDrawable(background);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            dialog.show();
        }
    }

    // region BottomSheet Methods
    public void showBottomSheet(AppCompatActivity activity) {
        if (countries == null || countries.isEmpty()) {
            throw new IllegalArgumentException(context.getString(R.string.error_no_countries_found));
        } else {
            activity.getLifecycle().addObserver(this);
            bottomSheetDialog = BottomSheetDialogView.newInstance(theme);
            bottomSheetDialog.setListener(this);
            bottomSheetDialog.show(activity.getSupportFragmentManager(), "bottomsheet");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void dismissDialogs() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void setupRecyclerView(View sheetView) {
        searchResults = new ArrayList<>();
        searchResults.addAll(countries);
        sortCountries(searchResults);
        adapter = new CountriesAdapter(sheetView.getContext(), searchResults,
                new OnItemClickListener() {
                    @Override
                    public void onItemClicked(Country country) {
                        if (onCountryPickerListener != null) {
                            onCountryPickerListener.onSelectCountry(country);
                            if (bottomSheetDialog != null) {
                                bottomSheetDialog.dismiss();
                            }
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            dialog = null;
                            bottomSheetDialog = null;
                            textColor = 0;
                            hintColor = 0;
                            backgroundColor = 0;
                            searchIconId = 0;
                            searchIcon = null;
                        }
                    }
                },
                textColor);
        countriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(sheetView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        countriesRecyclerView.setLayoutManager(layoutManager);
        countriesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setSearchEditText() {
        if (canSearch) {
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Intentionally Empty
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Intentionally Empty
                }

                @Override
                public void afterTextChanged(Editable searchQuery) {
                    search(searchQuery.toString());
                }
            });
            searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    InputMethodManager imm = (InputMethodManager) searchEditText.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
                    }
                    return true;
                }
            });
        } else {
            searchEditText.setVisibility(View.GONE);
        }
    }

    private void search(String searchQuery) {
        searchResults.clear();
        for (Country country : countries) {
            if (country.getName().toLowerCase(Locale.ENGLISH).contains(searchQuery.toLowerCase())) {
                searchResults.add(country);
            }
        }
        sortCountries(searchResults);
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void setCustomStyle(View sheetView) {
        if (style != 0) {
            int[] attrs =
                    {
                            android.R.attr.textColor, android.R.attr.textColorHint, android.R.attr.background,
                            android.R.attr.drawable
                    };
            TypedArray ta = sheetView.getContext().obtainStyledAttributes(style, attrs);
            textColor = ta.getColor(0, Color.BLACK);
            hintColor = ta.getColor(1, Color.GRAY);
            backgroundColor = ta.getColor(2, Color.WHITE);
            searchIconId = ta.getResourceId(3, R.drawable.ic_search);
            searchEditText.setTextColor(textColor);
            searchEditText.setHintTextColor(hintColor);
            searchIcon = ContextCompat.getDrawable(searchEditText.getContext(), searchIconId);
            if (searchIconId == R.drawable.ic_search) {
                searchIcon.setColorFilter(new PorterDuffColorFilter(hintColor, PorterDuff.Mode.SRC_ATOP));
            }
            searchEditText.setCompoundDrawablesWithIntrinsicBounds(searchIcon, null, null, null);
            rootView.setBackgroundColor(backgroundColor);
            ta.recycle();
        }
    }

    @Override
    public void initiateUi(View sheetView) {
        searchEditText = sheetView.findViewById(R.id.country_code_picker_search);
        countriesRecyclerView = sheetView.findViewById(R.id.countries_recycler_view);
        rootView = sheetView.findViewById(R.id.rootView);
    }
    // endregion

    public Country getCountryFromSIM() {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null
                && telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
            return getCountryByISO(telephonyManager.getSimCountryIso());
        }
        return null;
    }

    public Country getCountryByLocale(@NonNull Locale locale) {
        String countryIsoCode = locale.getCountry();
        return getCountryByISO(countryIsoCode);
    }

    public Country getCountryByName(@NonNull String countryName) {
        Collections.sort(countries, new NameComparator());
        Country country = new Country();
        country.setName(countryName);
        int i = Collections.binarySearch(countries, country, new NameComparator());
        if (i < 0) {
            return null;
        } else {
            return countries.get(i);
        }
    }

    public Country getCountryByISO(@NonNull String countryIsoCode) {
        Collections.sort(countries, new ISOCodeComparator());
        Country country = new Country();
        country.setCode(countryIsoCode);
        int i = Collections.binarySearch(countries, country, new ISOCodeComparator());
        if (i < 0) {
            return null;
        } else {
            return countries.get(i);
        }
    }

    public Country getCountryByDialCode(@NonNull String dialCode) {
        Collections.sort(countries, new DialCodeComparator());
        Country country = new Country();
        country.setDialCode(dialCode);
        int i = Collections.binarySearch(countries, country, new DialCodeComparator());
        if (i < 0) {
            return null;
        } else {
            return countries.get(i);
        }
    }
    // endregion

    // region Builder
    public static class Builder {
        private Context context;
        private int sortBy = SORT_BY_NONE;
        private boolean canSearch = true;
        private OnCountryPickerListener onCountryPickerListener;
        private int style;
        private int theme = THEME_NEW;

        public Builder with(@NonNull Context context) {
            this.context = context;
            return this;
        }

        public Builder style(@NonNull @StyleRes int style) {
            this.style = style;
            return this;
        }

        public Builder sortBy(@NonNull int sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public Builder listener(@NonNull OnCountryPickerListener onCountryPickerListener) {
            this.onCountryPickerListener = onCountryPickerListener;
            return this;
        }

        public Builder canSearch(@NonNull boolean canSearch) {
            this.canSearch = canSearch;
            return this;
        }

        public Builder theme(@NonNull int theme) {
            this.theme = theme;
            return this;
        }

        public CountryPicker build() {
            return new CountryPicker(this);
        }
    }
    // endregion

    // region Comparators
    private static class ISOCodeComparator implements Comparator<Country> {
        @Override
        public int compare(Country country, Country nextCountry) {
            return country.getCode().compareToIgnoreCase(nextCountry.getCode());
        }
    }

    private static class NameComparator implements Comparator<Country> {
        @Override
        public int compare(Country country, Country nextCountry) {
            return country.getName().compareToIgnoreCase(nextCountry.getName());
        }
    }

    public static class DialCodeComparator implements Comparator<Country> {
        @Override
        public int compare(Country country, Country nextCountry) {
            return country.getDialCode().compareTo(nextCountry.getDialCode());
        }
    }

    public static void showPicker(AppCompatActivity appCompatActivity, boolean dialogType, OnCountryPickerListener onCountryPickerListener) {
        Builder builder = new Builder().with(appCompatActivity).listener(onCountryPickerListener);
        builder.theme(CountryPicker.THEME_NEW);
        builder.canSearch(true);
        builder.sortBy(CountryPicker.SORT_BY_NAME);
        CountryPicker countryPicker = builder.build();
        if (dialogType) {
            countryPicker.showDialog(appCompatActivity);
        } else {
            countryPicker.showBottomSheet(appCompatActivity);
        }
    }
    // endregion
}
