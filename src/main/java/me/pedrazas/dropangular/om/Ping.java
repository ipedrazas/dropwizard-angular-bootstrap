package me.pedrazas.dropangular.om;

import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "pings")
@NamedQueries({
        @NamedQuery(
                name = "me.pedrazas.dropangular.om.Ping.findAll",
                query = "SELECT p FROM Ping p"
        )
})
public class Ping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	

	public Ping(String remoteIp) {
		super();
		this.remoteIp = remoteIp;
		this.timeStamp = System.currentTimeMillis();
	}



	@Column(name = "remote_ip", nullable = true)
	@JsonProperty("remote_ip")
	private String remoteIp;
	
	public Ping() {
		super();
		this.timeStamp = System.currentTimeMillis();
	}

	// This attribute has a name, a different json name
	// and a different column name.
	// Cannot get more diverse than this :)
	@Column(name = "ping_tstamp", nullable = false)
	@JsonProperty("timestamp")
	private Long timeStamp;
	
	public String getTimestampAsString(){
		DateTime jodaTime = new DateTime(this.timeStamp,DateTimeZone.forTimeZone(TimeZone.getTimeZone("EU/London")));
        DateTimeFormatter parser = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
        return parser.print(jodaTime);
	}

	
}
