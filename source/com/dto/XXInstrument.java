package com.dto;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XXInstrument {

	private Header Header;
	private Output Output;

	public Header getHeader() {
		return Header;
	}

	public void setHeader(Header header) {
		Header = header;
	}

	public Output getOutput() {
		return Output;
	}

	public void setOutput(Output output) {
		Output = output;
	}

	public String ConvertObjectToXMLBuffer() {
		try {
			// Create the JAXB Context
			JAXBContext jaxbContext = JAXBContext
					.newInstance("com.jaxbclasses");
			StringWriter stringWriter = new StringWriter();
			// Header & Output
			com.jaxbclasses.Service xsService = getXSService(this);
			// Create Marshaller
			Marshaller marshaller = (Marshaller) jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(xsService, stringWriter);

			// Now return the XML thus formed.
			return stringWriter.toString();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	private static String convertToStr(Object o) {
		return String.valueOf(o == null ? "" : o);
	}

	public static String generateXML(ResultSet rs, String businessDate,
			long recordId, String appId) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		XXInstrument s = new XXInstrument();
		try {
			Header header = new Header();
			header.setVersion("1.0.0");
			header.setMsgLanguage("en");
			header.setCreateDateTime(sdf.format(new Date()));
			header.setSenderCompId("STARR");
			header.setTargetCompId(appId);
			s.setHeader(header);
			Output o = new Output();

			Instruments intrs = new Instruments();
			List<Instrument> list = new ArrayList<Instrument>();
			Instrument intr = new Instrument();
			Main m = new Main();
			m.setSName(convertToStr(rs.getString("INSTR_SHORT_NAME")));
			m.setLName(convertToStr(rs.getString("INSTR_LONG_NAME")));
			intr.setMain(m);

			// Flag
			Flags flags = new Flags();
			Flag flag = new Flag();
			flag.setType("GFO");
			flag.setValue(convertToStr(rs.getString("GFO_FLAG")));
			flags.setFlag(flag);

			intr.setFlags(flags);

			list.add(intr);
			intrs.setInstrument(list);
			o.setInstruments(intrs);
			s.setOutput(o);
		} catch (SQLException e) {
			throw new SQLException();
		}
		return s.ConvertObjectToXMLBuffer();
	}

	private com.jaxbclasses.Service getXSService(XXInstrument service)
			throws JAXBException {
		com.jaxbclasses.ObjectFactory objectFactory = new com.jaxbclasses.ObjectFactory();
		// Get Root node name
		com.jaxbclasses.Service xsService = objectFactory.createService();
		com.jaxbclasses.Header xsHeader = objectFactory.createHeader();
		com.jaxbclasses.Output xsOut = objectFactory.createOutput();

		// fault-tolerant
		Header header = service.getHeader() == null ? new Header() : service
				.getHeader();
		// Header
		xsHeader.setVersion(header.getVersion());
		xsHeader.setCreateDateTime(header.getCreateDateTime());
		xsHeader.setSenderCompId(header.getSenderCompId());
		xsHeader.setTargetCompId(header.getTargetCompId());
		xsHeader.setMsgLanguage(header.getMsgLanguage());
		xsHeader.setServiceName(header.getServiceName());
		List<Instrument> instrs = null;
		try {
			instrs = service.getOutput().getInstruments().getInstrument();
		} catch (NullPointerException e) {
			instrs = new ArrayList<Instrument>();
		}
		com.jaxbclasses.Instruments xsInstruments = objectFactory
				.createInstruments();
		for (Instrument ins : instrs) {
			com.jaxbclasses.Instrument xsInstrument = objectFactory
					.createInstrument();
			// Main
			com.jaxbclasses.Main xsMain = objectFactory.createMain();
			if (ins.getMain() != null) {
				xsMain.setSName(ins.getMain().getSName());
				xsMain.setLName(ins.getMain().getLName());
			}
			xsInstrument.setMain(xsMain);

			// Flag
			com.jaxbclasses.Flags xsFlags = objectFactory.createFlags();
			if (ins.getFlags() != null) {
				com.jaxbclasses.Flag xsFlag = objectFactory.createFlag();
				if (ins.getFlags().getFlag() != null) {
					xsFlag.setType(ins.getFlags().getFlag().getType());
					xsFlag.setValue(ins.getFlags().getFlag().getValue());
				}
				xsFlags.getFlag().add(xsFlag);
			}

			xsInstrument.setFlags(xsFlags);

			// Add
			xsInstruments.getInstrument().add(xsInstrument);
		}
		xsOut.setInstruments(xsInstruments);
		xsService.setHeader(xsHeader);
		xsService.setOutput(xsOut);

		return xsService;
	}

	public String ConvertXMLBufferToObject(String inputXMLBuffer) {

		try {
			// Create the JAXB Context
			JAXBContext jaxbContext = JAXBContext
					.newInstance("com.jaxbclasses");

			// Get the unmarshaller and unmarshall inputXMLBuffer to get the
			// object
			Unmarshaller unmarshaller = (Unmarshaller) jaxbContext
					.createUnmarshaller();
			// unmarshaller.setValidating(false);
			com.jaxbclasses.Service xsService = (com.jaxbclasses.Service) unmarshaller
					.unmarshal(new ByteArrayInputStream(inputXMLBuffer
							.getBytes()));
			// Head
			com.jaxbclasses.Header xheader = xsService.getHeader();
			Header header = new Header();
			if (xheader != null) {
				header.setVersion(xheader.getVersion());
				header.setCreateDateTime(xheader.getCreateDateTime());
				header.setSenderCompId(xheader.getSenderCompId());
				header.setTargetCompId(xheader.getTargetCompId());
				header.setMsgLanguage(xheader.getMsgLanguage());
				header.setServiceName(xheader.getServiceName());
			}
			this.setHeader(header);
			// Output
			com.jaxbclasses.Output xoutput = xsService.getOutput();
			Output output = new Output();
			Instruments intruments = new Instruments();
			List<Instrument> list = new ArrayList<Instrument>();
			if (xoutput != null && xoutput.getInstruments() != null
					&& xoutput.getInstruments().getInstrument() != null) {
				List<com.jaxbclasses.Instrument> xins = xoutput
						.getInstruments().getInstrument();
				for (com.jaxbclasses.Instrument ins : xins) {
					Instrument intrument = new Instrument();
					// Main
					Main main = new Main();
					if (ins.getMain() != null) {
						main.setSName(ins.getMain().getSName());
						main.setLName(ins.getMain().getLName());
					}
					intrument.setMain(main);
					list.add(intrument);
				}
			}
			intruments.setInstrument(list);
			output.setInstruments(intruments);
			this.setOutput(output);
		} catch (JAXBException exception) {
			// ErrorNotificationUtil.sendErrorNotification(exception, this);
			exception.printStackTrace();
		}
		return null;
	}

}
