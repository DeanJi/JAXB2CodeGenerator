package com.test;

import java.util.ArrayList;
import java.util.List;

import com.dto.Flag;
import com.dto.Flags;
import com.dto.Header;
import com.dto.XXInstrument;
import com.dto.Instrument;
import com.dto.Instruments;
import com.dto.Main;
import com.dto.Output;

public class Test {

	public static void main(String[] args) {
		testXML2Object();
	}

	public static void testObject2XML() {

		XXInstrument s = new XXInstrument();
		Header header = new Header();
		header.setVersion("Version");
		header.setMsgLanguage("MsgLanguage");
		header.setCreateDateTime("2011-11-11 11:11:11");
		header.setSenderCompId("SenderCompId");
		header.setTargetCompId("TargetCompId");
		s.setHeader(header);
		Output o = new Output();

		Instruments intrs = new Instruments();
		Instrument intr = new Instrument();
		Instrument intr2 = new Instrument();

		Main m = new Main();
		m.setSName("SName");
		m.setLName("LName");
		Flags flags = new Flags();
		Flag flag = new Flag();
		flag.setType("Type");
		flag.setValue("Value");
		flags.setFlag(flag);
		intr.setMain(m);
		intr2.setMain(m);
		intr.setFlags(flags);
		intr2.setFlags(flags);

		List<Instrument> list = new ArrayList<Instrument>();
		list.add(intr);
		list.add(intr2);
		intrs.setInstrument(list);
		o.setInstruments(intrs);
		s.setOutput(o);
		String xml = s.ConvertObjectToXMLBuffer();
		System.out.println(xml);

	}

	public static void testXML2Object() {
		XXInstrument s = new XXInstrument();
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<Service>"
				+ "<Header ServiceName=\"ServiceName\" MsgLanguage=\"MsgLanguage\">"
				+ " <Version>Version</Version> "
				+ " <CreateDateTime>2011-11-11 11:11:11</CreateDateTime> "
				+ " <SenderCompId>SenderCompId</SenderCompId> "
				+ " <TargetCompId>TargetCompId</TargetCompId> "
				+ "</Header> "
				+ "<Output> "
				+ "<Instruments> "
				+ "<Instrument> "
				+ "<Main> "
				+ "<SName>SName</SName> "
				+ "<LName>LName</LName> "
				+ "</Main> "
				+ "<Flags> "
				+ "<Flag> "
				+ "<Type>Type</Type> "
				+ "<Value>Value</Value> "
				+ "</Flag> "
				+ "</Flags> "
				+ "</Instrument> "
				+ "<Instrument> "
				+ "<Main> "
				+ "  <SName>SName</SName> "
				+ "  <LName>LName</LName> "
				+ "</Main> "
				+ "<Flags> "
				+ "<Flag> "
				+ "<Type>Type</Type> "
				+ "<Value>Value</Value> "
				+ "</Flag> "
				+ "</Flags> "
				+ "</Instrument> "
				+ "</Instruments> "
				+ "</Output> "
				+ " </Service> ";
		s.ConvertXMLBufferToObject(xml);
		System.out.println(s.getHeader().getVersion());

	}

}
