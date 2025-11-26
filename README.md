# ThinkOrSwim-Docs


# How to use this pack in ThinkOrSwim (User Guide)

## A. Add as Studies

1. Open TOS → Charts tab.
2. Click Studies → Edit Studies…
3. Click New (bottom left).
4. Paste one of the thinkscript blocks (e.g., RSI with arrows).
5. Name it: e.g., RSI_14_Arrows_CMGStyle.
6. Click OK → Apply → OK.
7. Now add it to any chart via Studies → User Defined → your study name.

You’ll see arrows and signals that match the logic in your PPT.


## B. Use as Scan Filters (Stock Hacker)

- Go to Scan tab → Stock Hacker.
- Click Add filter → Study.
- In the Study dropdown, choose:
	- “Edit…” to paste simple scan expressions like
RSI(length = 14) crosses above 30, or
	- Choose one of your saved custom studies, then adjust inputs.

- Add additional filters:
	* Price > 20
	* Volume > 500,000
	* Sector filters, etc.

- Click Scan to get a live list of tickers matching the same kind of signals you see in the CMG PPT slides.

## C. Link to conditional orders

- Create a buy order for a stock in Trade → Active Trader or from the chart.

- In the order ticket, click the gear icon to open Order Rules.

- Under Conditions, attach a Study condition:
	* Example: “MACD crosses above Signal” or “RSI crosses above 30”.

- This lets the order trigger only when your technical condition happens.