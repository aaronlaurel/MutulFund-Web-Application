package edu.cmu.mgmt.common;

public enum TransactionType {

	BUY() {

		@Override
		public String toString() {
			return "buy";
		}
	},

	CHECK() {

		@Override
		public String toString() {
			return "check";
		}
	},

	DEPOSIT() {

		@Override
		public String toString() {
			return "deposit";
		}
	},

	SELL() {

		@Override
		public String toString() {
			return "sell";
		}
	};

	@Override
	public abstract String toString();
}
