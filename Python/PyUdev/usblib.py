#!/usr/bin/env python2


'''
Created on Jan 21, 2015

@author: andreas
'''

import usb1

def main():
    context = usb1.USBContext()
    for device in context.getDeviceList(skip_on_error=True):
        print '==================='
        print device.getManufacturer(), "/",  device.getProduct()
        print '-------------------'
        #print 'ID %04x:%04x' % (device.getVendorID(), device.getProductID()), \
        #                       '->'.join(str(x) for x in ['Bus %03i' % (device.getBusNumber(), )] + device.getPortNumberList()), \
        #                       'Device', \
        #                       device.getDeviceAddress(), \
        #                       device.getProduct()

if __name__ == '__main__':
    main()
