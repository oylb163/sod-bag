package edu.sc.seis.sod.bag;

import java.time.ZonedDateTime;

import edu.sc.seis.seisFile.fdsnws.stationxml.BaseNodeType;
import edu.sc.seis.sod.mock.seismogram.MockSeismogram;
import edu.sc.seis.sod.mock.station.MockChannelId;
import edu.sc.seis.sod.model.common.SamplingImpl;
import edu.sc.seis.sod.model.common.TimeInterval;
import edu.sc.seis.sod.model.common.UnitImpl;
import edu.sc.seis.sod.model.seismogram.LocalSeismogramImpl;
import edu.sc.seis.sod.util.time.ClockUtil;
import junit.framework.TestCase;

public class DecimateTest extends TestCase {

    public void testSimpleDecimate() throws Exception {
        int factor = 5;
        LocalSeismogramImpl seis = MockSeismogram.createTestData("test",
                                                                           new int[100],
                                                                           ZonedDateTime.now(BaseNodeType.TZ_UTC),
                                                                           MockChannelId.createVerticalChanId(),
                                                                           new SamplingImpl(20,
                                                                                            new TimeInterval(1,
                                                                                                             UnitImpl.SECOND)));
        Decimate decimate = new Decimate(factor);
        LocalSeismogramImpl out = decimate.apply(seis);
        assertEquals("seis length", seis.getNumPoints()/factor, out.getNumPoints());
        assertEquals("sampling period", seis.getSampling().getPeriod().getValue(UnitImpl.SECOND),
                     out.getSampling().getPeriod().getValue(UnitImpl.SECOND)/factor, 0.000001);
    }
}
