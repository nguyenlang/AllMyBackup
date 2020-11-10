package com.example.yuclientservice.Utils;


import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.Point;

public class Event {
    long startTime = 10;
    long duration = 10;

    Path path = null;

    GestureDescription.StrokeDescription onEvent()
    {
        path = new Path();
        movePath();
        return new GestureDescription.StrokeDescription(path, startTime, duration, false);
    }

    void movePath() {
    }
}

class Move extends Event {
    Point to;
    public Move(Point to)
    {
        this.to = to;
    }

    @Override
    void movePath()
    {
        path.moveTo(to.x, to.y);
    }
}

class Click extends Event {
    Point to;
    public Click(Point to)
    {
        this.to = to;
    }

    @Override
    void movePath()
    {
        path.moveTo(to.x, to.y);
    }
}

class Swipe extends Event {
    Point from, to;
    public Swipe(Point _to, Point _from)
    {
        from = _from;
        to = _to;
    }

    @Override
    void movePath()
    {
        path.moveTo(from.x, from.y);
        path.lineTo(to.x, to.y);
    }
}