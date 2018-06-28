package emserverapp;

public class Group {
	private int dbid, numtotal, nummale, numfemale;
	private String name, notes;
	private java.sql.Date startDate, endDate;
	
	/**
	 * Create a group, supplying all information.
	 * @param dbid database id
	 * @param numtotal number of total people in group
	 * @param nummale number of male people in group
	 * @param numfemale number of female people in group
	 * @param name name of group
	 * @param notes group notes
	 * @param startDate start date of group event(s)
	 * @param endDate end date of group event(s)
	 */
	public Group(int dbid, int numtotal, int nummale, int numfemale, String name, String notes, java.sql.Date startDate, java.sql.Date endDate) {
		this.setDBid(dbid);
		this.setNumTotal(numtotal);
		this.setNumMale(nummale);
		this.setNumFemale(numfemale);
		this.setName(name);
		this.setNotes(notes);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	/**
	 * Create a group, supplying all information EXCEPT database id.
	 * @param numtotal number of total people in group
	 * @param nummale number of male people in group
	 * @param numfemale number of female people in group
	 * @param name name of group
	 * @param notes group notes
	 * @param startDate start date of group event(s)
	 * @param endDate end date of group event(s)
	 */
	public Group(int numtotal, int nummale, int numfemale, String name, String notes, java.sql.Date startDate, java.sql.Date endDate) {
		this.setDBid(-1);
		this.setNumTotal(numtotal);
		this.setNumMale(nummale);
		this.setNumFemale(numfemale);
		this.setName(name);
		this.setNotes(notes);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	/**
	 * Create a group, supplying all information EXCEPT database id and group notes.
	 * @param numtotal number of total people in group
	 * @param nummale number of male people in group
	 * @param numfemale number of female people in group
	 * @param name name of group
	 * @param startDate start date of group event(s)
	 * @param endDate end date of group event(s)
	 */
	public Group(int numtotal, int nummale, int numfemale, String name, java.sql.Date startDate, java.sql.Date endDate) {
		this.setDBid(-1);
		this.setNumTotal(numtotal);
		this.setNumMale(nummale);
		this.setNumFemale(numfemale);
		this.setName(name);
		this.setNotes("");
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	/**
	 * Create a group, supplying all information EXCEPT database id and male/female numbers.
	 * @param numtotal number of total people in group
	 * @param name name of group
	 * @param notes group notes
	 * @param startDate start date of group event(s)
	 * @param endDate end date of group event(s)
	 */
	public Group(int numtotal, String name, String notes, java.sql.Date startDate, java.sql.Date endDate) {
		this.setDBid(-1);
		this.setNumTotal(numtotal);
		this.setNumMale(-1);
		this.setNumFemale(-1);
		this.setName(name);
		this.setNotes(notes);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	/**
	 * Create a group, supplying all information EXCEPT database id, male/female numbers, and group notes.
	 * @param numtotal number of total people in group
	 * @param name name of group
	 * @param start date of group event(s)
	 * @param endDate end date of group event(s)
	 */
	public Group(int numtotal, String name, java.sql.Date startDate, java.sql.Date endDate) {
		this.setDBid(-1);
		this.setNumTotal(numtotal);
		this.setNumMale(-1);
		this.setNumFemale(-1);
		this.setName(name);
		this.setNotes("");
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	/**
	 * Return the database id of the group (-1 for new/not synced group)
	 * @return database id
	 */
	public int getDBid() {
		return dbid;
	}

	/**
	 * Set the database id of the group (-1 for new/not synced group)
	 * @param dbid database id
	 */
	public void setDBid(int dbid) {
		this.dbid = dbid;
	}

	/**
	 * Return the number of total people in group
	 * @return number of total people in group
	 */
	public int getNumTotal() {
		return numtotal;
	}

	/**
	 * Set the number of total people in group
	 * @param numtotal number of total people in group
	 */
	public void setNumTotal(int numtotal) {
		this.numtotal = numtotal;
	}

	/**
	 * Return the number of total male people in group (-1 for not supplied)
	 * @return  total male people in group
	 */
	public int getNumMale() {
		return nummale;
	}

	/**
	 * Set the number of total male people in group (-1 for not supplied)
	 * @param nummale total male people in group
	 */
	public void setNumMale(int nummale) {
		this.nummale = nummale;
	}

	/**
	 * Return the number of total female people in group (-1 for not supplied)
	 * @return  total female people in group
	 */
	public int getNumFemale() {
		return numfemale;
	}

	/**
	 * Set the number of total female people in group (-1 for not supplied)
	 * @param nummale total female people in group
	 */
	public void setNumFemale(int numfemale) {
		this.numfemale = numfemale;
	}

	/**
	 * Returns the group name
	 * @return group name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the group name
	 * @param name group name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns notes for the group
	 * @return group notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * (re)sets notes to group notes
	 * @param notes group notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * Appends additional notes to group notes
	 * @param notes notes to append
	 */
	public void appendNotes(String notes) {
		this.notes += ";" + notes;
	}

	/**
	 * Returns start date of group event(s)
	 * @return start date
	 */
	public java.sql.Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets start date of group event(s)
	 * @param startDate start date
	 */
	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns end date of group event(s)
	 * @return end date
	 */
	public java.sql.Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets end date of group event(s)
	 * @param startDate end date
	 */
	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}
	
	public String toString() {
		return "dbid: " + this.getDBid() + ", numtotal: " + this.getNumTotal() + ", nummale: " + this.getNumMale() + ", numfemale: " + this.getNumFemale() +
				", Group Name: " + this.getName() + ", Group Notes: " + this.getNotes() +
				", Start Date: " + this.getStartDate().toString() + ", End Date: " + this.getEndDate().toString() +
				".";
	}
}
