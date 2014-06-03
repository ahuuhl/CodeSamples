# This work is licensed under the Creative Commons Attribution 3.0 Unported
# License. To view a copy of this license, visit
# http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
# Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

TEMPLATE = app
QT = gui core widgets

CONFIG += qt warn_on debug console

HEADERS = ScrollAreaLayout.h ScaleWidget.h ScaleEdgeWidget.h GraphicsSheet.h \
          LineItem.h RectItem.h CircleItem.h EllipseItem.h TextItem.h \
          Interactor.h EditFrameInteractor.h NewRectItemInteractor.h NewLineItemInteractor.h NewTextItemInteractor.h \
          NewCircleItemInteractor.h NewEllipseItemInteractor.h \
          Snapper.h LabelledComboBox.h Log.h MainWindow.h

SOURCES = ScrollAreaLayout.cpp ScaleWidget.cpp ScaleEdgeWidget.cpp GraphicsSheet.cpp \
          LineItem.cpp RectItem.cpp CircleItem.cpp EllipseItem.cpp TextItem.cpp \
          Interactor.cpp EditFrameInteractor.cpp NewRectItemInteractor.cpp NewLineItemInteractor.cpp NewTextItemInteractor.cpp \
          NewCircleItemInteractor.cpp NewEllipseItemInteractor.cpp \
          Snapper.cpp LabelledComboBox.cpp Log.cpp main.cpp

RESOURCES += GraphicsView.qrc