import cv2
from networktables import NetworkTables as NT

def nothing(x):
    pass

if __name__ == '__main__':

    NT.initialize(server='roborio-3211-FRC.local')
    dashboardTable = NT.getTable('SmartDashboard')

    cv2.namedWindow('image')

    cv2.createTrackbar('hMin', 'image', 0, 255, nothing)
    cv2.createTrackbar('hMax', 'image', 0, 255, nothing)

    cv2.createTrackbar('sMin', 'image', 0, 255, nothing)
    cv2.createTrackbar('sMax', 'image', 0, 255, nothing)

    cv2.createTrackbar('vMin', 'image', 0, 255, nothing)
    cv2.createTrackbar('vMax', 'image', 0, 255, nothing)

    cv2.createTrackbar('Set HSV', 'image', 0, 1, nothing)

    while(True):

        dashboardTable.putNumber('Set HSV', cv2.getTrackbarPos('Set HSV', 'image'))

        dashboardTable.putNumber('hMin', cv2.getTrackbarPos('hMin', 'image'))
        dashboardTable.putNumber('hMax', cv2.getTrackbarPos('hMax', 'image'))

        dashboardTable.putNumber('sMin', cv2.getTrackbarPos('sMin', 'image'))
        dashboardTable.putNumber('sMax', cv2.getTrackbarPos('sMax', 'image'))

        dashboardTable.putNumber('vMin', cv2.getTrackbarPos('vMin', 'image'))
        dashboardTable.putNumber('vMax', cv2.getTrackbarPos('vMax', 'image'))

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
