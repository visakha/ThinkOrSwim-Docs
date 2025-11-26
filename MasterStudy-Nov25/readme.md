# CMG Master Study -- ThinkOrSwim Scan & Usage Guide

This README explains how to use the **CMG_Master_Indicators**
ThinkOrSwim Master Study for scanning and signal generation exactly as
used in your CMG technical analysis project.

------------------------------------------------------------------------

## 1. How to Use This Master Study in Stock Hacker (Scan Tab)

1.  Open **ThinkOrSwim → Scan tab → Stock Hacker**.
2.  Click **Add filter → Study**.
3.  In the Study dropdown:
    -   Choose **Custom → Edit...**
    -   In the *Study* field, select your saved study:
        `CMG_Master_Indicators`
    -   In the *Plot* field, select the signal you want to scan for
        (example: `RSI_Buy`).
4.  Set the condition to:
    -   **is true**
    -   **within 1 bar**
5.  Add any additional filters such as:
    -   Price
    -   Market Cap
    -   Volume
    -   Sector
6.  Click **Scan**.

------------------------------------------------------------------------

## 2. Common Scan Presets (Using Master Study Plot Names)

### 1. RSI Bullish Reversal Scan

-   Study: `CMG_Master_Indicators`
-   Plot: `RSI_Buy`
-   Condition: `is true`

Finds stocks where **RSI just crossed above 30**, signaling exit from
oversold.

------------------------------------------------------------------------

### 2. MACD Bullish Crossover Scan

-   Study: `CMG_Master_Indicators`
-   Plot: `MACD_Buy`
-   Condition: `is true`

Finds stocks where **MACD line crosses above Signal line**.

------------------------------------------------------------------------

### 3. Bollinger Squeeze Breakout (Upside)

-   Study: `CMG_Master_Indicators`
-   Plot: `BB_Squeeze_Breakout_Up`
-   Condition: `is true`

Finds stocks coming out of low volatility squeezes into **upside
breakouts**.

------------------------------------------------------------------------

### 4. 10 & 20 Day SMA Bullish Cross

-   Study: `CMG_Master_Indicators`
-   Plot: `SMA10_20_Buy`
-   Condition: `is true`

Identifies **short‑term momentum trend reversals to the upside**.

Also available: - `SMA20_50_Buy` -- Intermediate trend - `SMA50_200_Buy`
-- Long‑term Golden Cross

------------------------------------------------------------------------

### 5. Stochastic Bullish Turn

-   Study: `CMG_Master_Indicators`
-   Plot: `Stoch_Buy`
-   Condition: `is true`

Finds stocks where **Stochastic %K turns up from below 20**.

------------------------------------------------------------------------

### 6. VROC Volume Surge Scan

-   Study: `CMG_Master_Indicators`
-   Plot: `VROC_Spike_Up`
-   Condition: `is true`

Finds stocks experiencing **unusual bullish volume expansion**.

------------------------------------------------------------------------

### 7. DMI Strong Uptrend Scan

-   Study: `CMG_Master_Indicators`
-   Plot: `DMI_Buy`
-   Condition: `is true`

Finds stocks where: - +DI crosses above --DI - ADX \> 20 Indicating
**confirmed trend strength**.

------------------------------------------------------------------------

### 8. Parabolic SAR Bullish Flip

-   Study: `CMG_Master_Indicators`
-   Plot: `PSAR_Buy`
-   Condition: `is true`

Finds stocks where **Parabolic SAR flipped below price**, signaling
potential trend continuation.

------------------------------------------------------------------------

### 9. Deep Pullback to SMA20 (Dip Buy Logic)

-   Study: `CMG_Master_Indicators`
-   Plot: `Dist_Extreme_Down`
-   Condition: `is true`

Finds stocks trading **more than X% below the 20‑day SMA** (default
15%).

------------------------------------------------------------------------

## 3. Suggested Scan Combinations

### Growth Breakout Scan

Use together: - `BB_Squeeze_Breakout_Up` - `MACD_Buy` -
`VROC_Spike_Up` - Volume \> 500,000

------------------------------------------------------------------------

### Strong Trend Continuation Scan

Use together: - `SMA20_50_Buy` - `DMI_Buy` - `PSAR_Buy`

------------------------------------------------------------------------

### Oversold Reversion Scan

Use together: - `RSI_Buy` - `Stoch_Buy` - `Dist_Extreme_Down`

------------------------------------------------------------------------

## 4. How to Use in Conditional Orders

1.  Create a Buy Order in the **Trade tab**.
2.  Click the **gear icon** beside the order (Order Rules).
3.  Add a **Study Condition**.
4.  Select:
    -   Study: `CMG_Master_Indicators`
    -   Plot: (example: `MACD_Buy`)
5.  Set **is true within 1 bar**.
6.  Validate and submit the conditional order.

------------------------------------------------------------------------

## 5. Notes

-   This Master Study is **ticker‑agnostic** and works for any stock.
-   All conditions match the signal logic used in the CMG technical
    analysis PPT.
-   Designed for:
    -   Swing Trading
    -   Trend Following
    -   Volatility Breakout Strategies
    -   Momentum Trading

------------------------------------------------------------------------

**Authored for CMG Technical Training System**
