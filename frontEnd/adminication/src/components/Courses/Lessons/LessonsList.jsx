import React, { useState } from "react";
import { Accordion, AccordionItem } from "react-light-accordion";
import "react-light-accordion/demo/css/index.css";
import AddAttendesTable from "./AddAttendancesTable";
import AttendanceTable from "./AttendanceTable";
import "./lessons.css";
import Description from "./Description";

export default function LessonsList(props) {
  const { lessons, attendances, students } = props;

  return (
    <div className="lessonsAccordion">
      <Accordion atomic={true}>
        {lessons.map((lesson, index) => {
          return attendances.filter((a) => a.lessonId === lesson.id).length >
            0 ? (
            <AccordionItem key={lesson.id} id={lesson.id} title={lesson.date}>
              <div className="lessonContent">
                <Description
                  lessonId={lesson.id}
                  description={lesson.description}
                ></Description>

                <AttendanceTable
                  courseId={lesson.courseId}
                  students={students}
                  attendances={
                    attendances
                      ? attendances.filter((a) => a.lessonId === lesson.id)
                      : null
                  }
                ></AttendanceTable>
              </div>
            </AccordionItem>
          ) : (
            <AccordionItem key={lesson.id} title={lesson.date}>
              <div className="lessonContent">
                {lesson.description}
                <AddAttendesTable
                  courseId={lesson.courseId}
                  students={students}
                  lessonId={lesson.id}
                  attendances={
                    attendances
                      ? attendances.filter((a) => a.lessonId === lesson.id)
                      : null
                  }
                ></AddAttendesTable>
              </div>
            </AccordionItem>
          );
        })}
      </Accordion>
    </div>
  );
}
