package tech.coner.trailer.client.motorsportreg.model

object TestAssignments {

    object TestEvent {

        val REBECCA_JACKSON_DAY_1 by lazy {
            tech.coner.trailer.client.motorsportreg.model.Assignment(
                attendeeId = "rebecca-jackson-attendee-id",
                city = "City",
                classLong = "Class",
                classId = "class-identifier",
                classModifierLong = "",
                classModifierId = "",
                classModifierShort = "",
                classShort = "CLS",
                vehicleColor = "Yes",
                firstName = "Rebecca",
                id = "rebecca-jackson-assignment-0",
                lastName = "Jackson",
                vehicleMake = "Make",
                clubMemberId = "1807",
                motorsportRegMemberUri = "/members/rebecca-jackson-member-id",
                vehicleModel = "Model",
                profileUri = "/profiles/rebecca-jackson-profile-id",
                region = "Region",
                segment = "Day 1",
                segmentUri = "/events/test-event-id/segments/day-1-segment-id",
                sponsor = "",
                status = "On Hold",
                tireBrand = "Tire Brand",
                vehicleNumber = "8",
                vehicleStatus = "Pending",
                vehicleUri = "/members/rebecca-jackson-member-id/vehicles/vehicle-id",
                vehicleYear = "2002"
            )
        }
        val REBECCA_JACKSON_DAY_2 by lazy {
            tech.coner.trailer.client.motorsportreg.model.Assignment(
                attendeeId = "rebecca-jackson-attendee-id",
                city = "City",
                classLong = "Class",
                classId = "class-identifier",
                classModifierLong = "",
                classModifierId = "",
                classModifierShort = "",
                classShort = "CLS",
                vehicleColor = "Yes",
                firstName = "Rebecca",
                id = "rebecca-jackson-assignment-id-1",
                lastName = "Jackson",
                vehicleMake = "Make",
                clubMemberId = "1807",
                motorsportRegMemberUri = "/members/rebecca-jackson-member-id",
                vehicleModel = "Model",
                profileUri = "/profiles/rebecca-jackson-profile-id",
                region = "Region",
                segment = "Day 2",
                segmentUri = "/events/test-event-id/segments/day-2-segment-id",
                sponsor = "",
                status = "On Hold",
                tireBrand = "Tire Brand",
                vehicleNumber = "8",
                vehicleStatus = "Pending",
                vehicleUri = "/members/rebecca-jackson-member-id/vehicles/vehicle-id",
                vehicleYear = "2002"
            )
        }
        val REBECCA_JACKSON_INSTRUCTOR by lazy {
            tech.coner.trailer.client.motorsportreg.model.Assignment(
                attendeeId = "rebecca-jackson-attendee-id",
                city = "City",
                classLong = "",
                classId = "",
                classModifierLong = "",
                classModifierId = "",
                classModifierShort = "",
                classShort = "",
                vehicleColor = "",
                firstName = "Rebecca",
                id = "rebecca-jackson-assignment-id-2",
                lastName = "Jackson",
                vehicleMake = "",
                clubMemberId = "1807",
                motorsportRegMemberUri = "/members/rebecca-jackson-member-id",
                vehicleModel = "",
                profileUri = "/profiles/rebecca-jackson-profile-id",
                region = "Region",
                segment = "Instructors",
                segmentUri = "/events/test-event-id/segments/instructors-segment-id",
                sponsor = "",
                status = "On Hold",
                tireBrand = "",
                vehicleNumber = "",
                vehicleStatus = "Pending",
                vehicleUri = "/members/rebecca-jackson-member-id/vehicles/",
                vehicleYear = ""
            )
        }

        val ALL_REBECCA_JACKSON by lazy {
            listOf(
                tech.coner.trailer.client.motorsportreg.model.TestAssignments.TestEvent.REBECCA_JACKSON_DAY_1,
                tech.coner.trailer.client.motorsportreg.model.TestAssignments.TestEvent.REBECCA_JACKSON_DAY_2,
                tech.coner.trailer.client.motorsportreg.model.TestAssignments.TestEvent.REBECCA_JACKSON_INSTRUCTOR
            )
        }
    }
}